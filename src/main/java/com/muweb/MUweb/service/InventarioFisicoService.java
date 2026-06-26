package com.muweb.MUweb.service;

import com.muweb.MUweb.model.*;
import com.muweb.MUweb.repository.DetalleInventarioFisicoRepository;
import com.muweb.MUweb.repository.InventarioFisicoRepository;
import com.muweb.MUweb.repository.MaterialRepository;
import com.muweb.MUweb.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioFisicoService {

    private final InventarioFisicoRepository inventarioFisicoRepository;
    private final DetalleInventarioFisicoRepository detalleRepository;
    private final MaterialRepository materialRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public InventarioFisico iniciarConteo(Long usuarioId) {
        Usuario encargado = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Encargado de logística no encontrado"));

        InventarioFisico nuevoInventario = InventarioFisico.builder()
                .usuario(encargado)
                .estado(EstadoInventario.EN_PROCESO)
                .build();

        return inventarioFisicoRepository.save(nuevoInventario);
    }

    @Transactional
    public DetalleInventarioFisico registrarDetalleConteo(Long inventarioId, Long materialId, Integer cantidadFisica) {
        InventarioFisico inventario = inventarioFisicoRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Proceso de inventario no encontrado"));

        if (inventario.getEstado() == EstadoInventario.FINALIZADO) {
            throw new IllegalStateException("No se pueden agregar detalles a un inventario finalizado.");
        }

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        Integer cantidadTeorica = material.getCantidadActual();
        Integer diferencia = cantidadFisica - cantidadTeorica;

        DetalleInventarioFisico detalle = DetalleInventarioFisico.builder()
                .inventarioFisico(inventario)
                .material(material)
                .cantidadTeorica(cantidadTeorica)
                .cantidadFisica(cantidadFisica)
                .diferencia(diferencia)
                .build();

        return detalleRepository.save(detalle);
    }

    @Transactional
    public InventarioFisico finalizarConteo(Long inventarioId) {
        InventarioFisico inventario = inventarioFisicoRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Proceso de inventario no encontrado"));
                
        inventario.setEstado(EstadoInventario.FINALIZADO);
        return inventarioFisicoRepository.save(inventario);
    }

    public List<InventarioFisico> obtenerInventariosPorEstado(EstadoInventario estado) {
        return inventarioFisicoRepository.findByEstado(estado);
    }

    public List<DetalleInventarioFisico> obtenerDetallesDeInventario(Long inventarioId) {
        return detalleRepository.findByInventarioFisicoId(inventarioId);
    }
}