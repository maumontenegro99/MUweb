package com.muweb.MUweb.controller;

import com.muweb.MUweb.model.AlertaStock;
import com.muweb.MUweb.service.AlertaStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AlertaStockController {

    private final AlertaStockService alertaStockService;

    @GetMapping
    public ResponseEntity<List<AlertaStock>> obtenerAlertasActivas() {
        return ResponseEntity.ok(alertaStockService.obtenerAlertasActivas());
    }

    @PutMapping("/{id}/procesar")
    public ResponseEntity<AlertaStock> procesarAlerta(@PathVariable Long id) {
        return ResponseEntity.ok(alertaStockService.procesarAlerta(id));
    }
}