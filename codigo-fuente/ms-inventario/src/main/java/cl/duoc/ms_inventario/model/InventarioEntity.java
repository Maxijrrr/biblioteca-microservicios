package cl.duoc.ms_inventario.model;

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
@Table(name = "inventario")


public class InventarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "id_sucursal", nullable = false)
    private Long idSucursal;

    @Column(name = "stock_total", nullable = false)
    private Integer stockTotal;

    @Column(name = "stock_disponible", nullable = false)
    private Integer stockDisponible;
}
