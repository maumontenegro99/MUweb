package com.muweb.MUweb.repository;

import com.muweb.MUweb.model.AlertaStock;
import com.muweb.MUweb.model.EstadoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaStockRepository extends JpaRepository<AlertaStock, Long> {
    
    List<AlertaStock> findByEstado(EstadoAlerta estado);
    
    // Verifica si ya existe una alerta sin procesar para evitar duplicados automáticos
    boolean existsByMaterialIdAndEstado(Long materialId, EstadoAlerta estado);
}