package com.muweb.MUweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalles_inventario_fisico")
public class DetalleInventarioFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_fisico_id", nullable = false)
    private InventarioFisico inventarioFisico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name = "cantidad_teorica", nullable = false)
    private Integer cantidadTeorica;

    @Column(name = "cantidad_fisica", nullable = false)
    private Integer cantidadFisica;

    @Column(nullable = false)
    private Integer diferencia;
}