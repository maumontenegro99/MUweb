package com.muweb.MUweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventarios_fisicos")
public class InventarioFisico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_conteo")
    private LocalDateTime fechaConteo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInventario estado;

    @OneToMany(mappedBy = "inventarioFisico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleInventarioFisico> detalles;

    @PrePersist
    protected void onCreate() {
        this.fechaConteo = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoInventario.EN_PROCESO;
        }
    }
}