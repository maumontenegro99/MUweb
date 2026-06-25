package com.muweb.MUweb.controller;

import com.muweb.MUweb.model.DetalleInventarioFisico;
import com.muweb.MUweb.model.EstadoInventario;
import com.muweb.MUweb.model.InventarioFisico;
import com.muweb.MUweb.service.InventarioFisicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InventarioFisicoController {

    private final InventarioFisicoService inventarioFisicoService;

    @PostMapping("/iniciar")
    public ResponseEntity<InventarioFisico> iniciarConteo(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(inventarioFisicoService.iniciarConteo(usuarioId));
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<DetalleInventarioFisico> registrarDetalle(@PathVariable Long id, @RequestParam Long materialId, @RequestParam Integer cantidadFisica) {
        return ResponseEntity.ok(inventarioFisicoService.registrarDetalleConteo(id, materialId, cantidadFisica));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<InventarioFisico> finalizarConteo(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioFisicoService.finalizarConteo(id));
    }

    @GetMapping
    public ResponseEntity<List<InventarioFisico>> obtenerPorEstado(@RequestParam EstadoInventario estado) {
        return ResponseEntity.ok(inventarioFisicoService.obtenerInventariosPorEstado(estado));
    }
}