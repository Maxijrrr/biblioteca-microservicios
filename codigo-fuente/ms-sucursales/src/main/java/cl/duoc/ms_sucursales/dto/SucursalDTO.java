package cl.duoc.ms_sucursales.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SucursalDTO {
    private Long idSucursal;

    @NotBlank(message = "El nombre de la sede es obligatorio")
    private String nombreSede;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
}
