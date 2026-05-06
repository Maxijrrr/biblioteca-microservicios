package cl.duoc.ms_prestamos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrestamoResponseDTO {
    private Long idPrestamo;
    private Long idPerfil;
    private String isbn;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionEsperada;
    private String estado;
}
