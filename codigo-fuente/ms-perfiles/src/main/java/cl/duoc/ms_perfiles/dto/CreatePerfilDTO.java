package cl.duoc.ms_perfiles.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePerfilDTO(
    @NotBlank(message = "El RUT es obligatorio")
        @Size(min = 9, max = 10, message = "El RUT debe tener entre 9 y 10 caracteres")
        String rut,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
        String correo,

        @NotBlank(message = "La carrera es obligatoria")
        @Size(min = 3, max = 100, message = "La carrera debe tener entre 3 y 100 caracteres")
        String carrera
) {
    
}
