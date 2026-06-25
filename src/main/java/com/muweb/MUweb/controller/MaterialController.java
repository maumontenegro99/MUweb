package com.muweb.MUweb.controller;

import com.muweb.MUweb.model.Material;
import com.muweb.MUweb.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    public ResponseEntity<List<Material>> obtenerTodos() {
        return ResponseEntity.ok(materialService.obtenerTodos());
    }

    @PostMapping("/{id}/recepcion")
    public ResponseEntity<Material> registrarRecepcion(@PathVariable Long id, @RequestParam Integer cantidad, @RequestParam Long usuarioId) {
        return ResponseEntity.ok(materialService.registrarRecepcion(id, cantidad, usuarioId));
    }

    @PutMapping("/{id}/ajuste")
    public ResponseEntity<Material> actualizarStockManual(@PathVariable Long id, @RequestParam Integer nuevaCantidad, @RequestParam String justificacion, @RequestParam Long usuarioId) {
        return ResponseEntity.ok(materialService.actualizarStockManual(id, nuevaCantidad, justificacion, usuarioId));
    }
}