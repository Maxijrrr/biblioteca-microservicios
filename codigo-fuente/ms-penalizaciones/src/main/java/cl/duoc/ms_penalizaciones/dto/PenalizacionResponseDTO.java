package cl.duoc.ms_penalizaciones.dto;

public record PenalizacionResponseDTO(
        Long idPenalizacion,
        Long idPerfil,
        Long idPrestamo,
        Double monto,
        boolean estado    
) {
    
}
