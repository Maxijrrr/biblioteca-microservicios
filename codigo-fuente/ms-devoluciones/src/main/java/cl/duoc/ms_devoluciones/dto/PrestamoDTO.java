package cl.duoc.ms_devoluciones.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrestamoDTO {
    private Long idPrestamo;
    private Long idPerfil;
    private String isbn;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionEsperada;
    private String estado;
}
