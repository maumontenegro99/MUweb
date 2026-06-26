package com.muweb.MUweb.service;

import com.muweb.MUweb.model.AlertaStock;
import com.muweb.MUweb.model.EstadoAlerta;
import com.muweb.MUweb.model.Material;
import com.muweb.MUweb.repository.AlertaStockRepository;
import com.muweb.MUweb.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaStockService {

    private final AlertaStockRepository alertaStockRepository;
    private final MaterialRepository materialRepository;

    @Transactional
    public void verificarUmbrales() {
        List<Material> materialesCriticos = materialRepository.findMaterialesConStockCritico();

        for (Material material : materialesCriticos) {
            boolean existeAlerta = alertaStockRepository.existsByMaterialIdAndEstado(material.getId(), EstadoAlerta.ACTIVA);
            
            if (!existeAlerta) {
                AlertaStock nuevaAlerta = AlertaStock.builder()
                        .material(material)
                        .mensaje("Stock crítico alcanzado para: " + material.getCodigo() + " - " + material.getDescripcion())
                        .estado(EstadoAlerta.ACTIVA)
                        .build();
                alertaStockRepository.save(nuevaAlerta);
            }
        }
    }

    public List<AlertaStock> obtenerAlertasActivas() {
        return alertaStockRepository.findByEstado(EstadoAlerta.ACTIVA);
    }

    @Transactional
    public AlertaStock procesarAlerta(Long alertaId) {
        AlertaStock alerta = alertaStockRepository.findById(alertaId)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alerta.setEstado(EstadoAlerta.PROCESADA);
        return alertaStockRepository.save(alerta);
    }
}