package cl.duoc.ms_penalizaciones.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "penalizaciones")

public class PenalizacionEntity {

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
    private boolean estado;

}
