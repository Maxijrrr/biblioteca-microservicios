package cl.duoc.ms_prestamos.dto;

import lombok.Data;

@Data
public class PerfilDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String correo;
    private String carrera;
}
