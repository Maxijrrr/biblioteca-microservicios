package cl.duoc.ms_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_perfil", nullable = false)
    private Long idPerfil;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;
}
