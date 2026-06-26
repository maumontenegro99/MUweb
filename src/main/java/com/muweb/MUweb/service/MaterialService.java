package com.muweb.MUweb.service;

import com.muweb.MUweb.model.*;
import com.muweb.MUweb.repository.HistorialAjusteRepository;
import com.muweb.MUweb.repository.MaterialRepository;
import com.muweb.MUweb.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final HistorialAjusteRepository historialAjusteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlertaStockService alertaStockService;

    public List<Material> obtenerTodos() {
        return materialRepository.findAll();
    }

    @Transactional
    public Material registrarRecepcion(Long materialId, Integer cantidadRecibida, Long usuarioId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        material.setCantidadActual(material.getCantidadActual() + cantidadRecibida);
        Material materialActualizado = materialRepository.save(material);

        HistorialAjuste historial = HistorialAjuste.builder()
                .material(materialActualizado)
                .usuario(usuario)
                .tipoMovimiento(TipoMovimiento.RECEPCION)
                .cantidadVariacion(cantidadRecibida)
                .build();
        historialAjusteRepository.save(historial);

        return materialActualizado;
    }

    @Transactional
    public Material actualizarStockManual(Long materialId, Integer nuevaCantidad, String justificacion, Long usuarioId) {
        // Validación de la HU-05: Justificación obligatoria
        if (justificacion == null || justificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La justificación es obligatoria para ajustes manuales.");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        int variacion = nuevaCantidad - material.getCantidadActual();
        material.setCantidadActual(nuevaCantidad);
        Material materialActualizado = materialRepository.save(material);

        HistorialAjuste historial = HistorialAjuste.builder()
                .material(materialActualizado)
                .usuario(usuario)
                .tipoMovimiento(TipoMovimiento.AJUSTE_MANUAL)
                .cantidadVariacion(variacion)
                .justificacion(justificacion)
                .build();
        historialAjusteRepository.save(historial);

        // Verificamos inmediatamente si este ajuste hizo que el stock cayera a niveles críticos
        alertaStockService.verificarUmbrales();

        return materialActualizado;
    }
}