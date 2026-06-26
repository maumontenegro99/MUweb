package com.muweb.MUweb.repository;

import com.muweb.MUweb.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    Optional<Material> findByCodigo(String codigo);

    // Consulta nativa de JPA para detectar qué materiales llegaron a su punto crítico
    @Query("SELECT m FROM Material m WHERE m.cantidadActual <= m.stockMinimo")
    List<Material> findMaterialesConStockCritico();
}