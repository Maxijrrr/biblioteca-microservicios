package cl.duoc.ms_prestamos.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class PrestamoDTO {
    @NotNull(message = "El ID de perfil es obligatorio")
    private Long idPerfil;
    
    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;
}