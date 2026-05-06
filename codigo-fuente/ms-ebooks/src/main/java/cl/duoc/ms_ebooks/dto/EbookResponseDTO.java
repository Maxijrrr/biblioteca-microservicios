package cl.duoc.ms_ebooks.dto;

import lombok.Data;

@Data
public class EbookResponseDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private boolean disponible;
    private boolean activo;
}
