package cl.duoc.ms_devoluciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DevolucionDTO {
    private Long idDevolucion;

    @NotNull(message = "El ID del préstamo es obligatorio")
    private Long idPrestamo;

    private LocalDateTime fechaEntrega; // Opcional en el request, se autogenera si es null
}
