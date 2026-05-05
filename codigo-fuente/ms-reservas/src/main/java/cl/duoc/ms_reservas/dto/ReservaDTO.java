package cl.duoc.ms_reservas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaDTO {
    private Long idReserva;

    @NotNull(message = "El ID del perfil es obligatorio")
    private Long idPerfil;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotNull(message = "La prioridad es obligatoria")
    @Min(value = 1, message = "La prioridad debe ser mayor o igual a 1")
    private Integer prioridad;
}
