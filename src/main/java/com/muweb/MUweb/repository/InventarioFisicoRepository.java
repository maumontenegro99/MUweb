package com.muweb.MUweb.repository;

import com.muweb.MUweb.model.InventarioFisico;
import com.muweb.MUweb.model.EstadoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioFisicoRepository extends JpaRepository<InventarioFisico, Long> {
    
    List<InventarioFisico> findByEstado(EstadoInventario estado);
}