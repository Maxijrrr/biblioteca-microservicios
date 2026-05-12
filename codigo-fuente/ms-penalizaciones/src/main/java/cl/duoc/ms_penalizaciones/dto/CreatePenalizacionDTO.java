package cl.duoc.ms_penalizaciones.dto;

import java.math.BigInteger;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Valid

public record CreatePenalizacionDTO(
        @NotNull(message = "El ID de perfil es obligatorio") Long idPerfil,
        @NotNull(message = "El ID de prestamo es obligatorio") Long idPrestamo,
        @NotNull(message = "El monto es obligatorio") @Positive(message = "El monto debe ser mayor a 0") BigInteger monto,

        boolean estado) {

}
