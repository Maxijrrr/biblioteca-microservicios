package cl.duoc.ms_penalizaciones.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "penalizaciones")
@Data
public class Penalizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_penalizacion")
    private Long idPenalizacion;

    @Column(name = "id_perfil", nullable = false)
    private Long idPerfil;

    @Column(name = "id_prestamo", nullable = false)
    private Long idPrestamo;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "estado", nullable = false)
    private String estado; // Pendiente, Pagada

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;
}
