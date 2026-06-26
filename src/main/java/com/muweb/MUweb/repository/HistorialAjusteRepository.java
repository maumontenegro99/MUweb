package com.muweb.MUweb.repository;

import com.muweb.MUweb.model.HistorialAjuste;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialAjusteRepository extends JpaRepository<HistorialAjuste, Long> {
    
    List<HistorialAjuste> findByMaterialIdOrderByFechaRegistroDesc(Long materialId);
}