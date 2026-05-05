package cl.duoc.ms_sucursales.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sucursales")
@Data
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @Column(name = "nombre_sede", nullable = false)
    private String nombreSede;

    @Column(name = "direccion", nullable = false)
    private String direccion;
}
