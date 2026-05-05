package cl.duoc.ms_catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LibroDTO {
    private Long idLibro;
    
    @NotBlank(message = "El ISBN no puede estar vacio")
    private String isbn;
    
    @NotBlank(message = "El titulo no puede estar vacio")
    private String titulo;
    
    @NotBlank(message = "El autor no puede estar vacio")
    private String autor;
    
    private String editorial;
    private Integer anioPublicacion;
    private String categoria;
}
