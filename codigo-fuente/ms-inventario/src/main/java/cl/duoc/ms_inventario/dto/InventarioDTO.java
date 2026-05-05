package cl.duoc.ms_inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioDTO {
    private Long idInventario;
    
    @NotBlank(message = "El ISBN no puede estar vacio")
    private String isbn;
    
    @NotNull(message = "El ID de sucursal es obligatorio")
    private Long idSucursal;
    
    @NotNull(message = "El stock total es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockTotal;
    
    @NotNull(message = "El stock disponible es obligatorio")
    @Min(value = 0, message = "El stock disponible no puede ser negativo")
    private Integer stockDisponible;
}
