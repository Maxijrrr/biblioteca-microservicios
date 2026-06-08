package cl.duoc.ms_devoluciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "devoluciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devolucion")
    private Long idDevolucion;

    @Column(name = "id_prestamo", nullable = false)
    private Long idPrestamo;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDateTime fechaEntrega;
}
