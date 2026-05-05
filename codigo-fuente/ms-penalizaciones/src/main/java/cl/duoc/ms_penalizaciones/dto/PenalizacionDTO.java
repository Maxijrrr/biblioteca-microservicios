package cl.duoc.ms_penalizaciones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PenalizacionDTO {
    private Long idPenalizacion;
    
    @NotNull(message = "El ID del perfil es obligatorio")
    private Long idPerfil;
    
    @NotNull(message = "El ID del prestamo es obligatorio")
    private Long idPrestamo;
    
    @NotNull(message = "El monto es obligatorio")
    @Min(value = 0, message = "El monto no puede ser negativo")
    private Double monto;
    
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    
    private LocalDate fechaCreacion;
}
