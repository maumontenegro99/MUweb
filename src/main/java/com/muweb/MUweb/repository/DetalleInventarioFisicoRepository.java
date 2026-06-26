package com.muweb.MUweb.repository;

import com.muweb.MUweb.model.DetalleInventarioFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleInventarioFisicoRepository extends JpaRepository<DetalleInventarioFisico, Long> {
    
    List<DetalleInventarioFisico> findByInventarioFisicoId(Long inventarioFisicoId);
}