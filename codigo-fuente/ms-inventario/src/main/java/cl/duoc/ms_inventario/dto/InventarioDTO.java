package cl.duoc.ms_inventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record InventarioDTO(
     
    @NotBlank(message = "El ISBN no puede estar vacio")
    String isbn,
    
    @NotNull(message = "El ID de sucursal es obligatorio")
    Long idSucursal,
    
    @NotNull(message = "El stock total es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
 Integer stockTotal,
    
    @NotNull(message = "El stock disponible es obligatorio")
    @Min(value = 0, message = "El stock disponible no puede ser negativo")
     Integer stockDisponible
) {
}

