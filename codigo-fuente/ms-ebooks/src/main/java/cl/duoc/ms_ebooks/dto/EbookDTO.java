package cl.duoc.ms_ebooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbookDTO {
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
}
