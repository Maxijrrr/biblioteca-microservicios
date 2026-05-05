package cl.duoc.ms_catalogo.service;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import java.util.List;

public interface LibroService {
    List<LibroDTO> listarTodos();
    LibroDTO obtenerPorId(Long id);
    LibroDTO obtenerPorIsbn(String isbn);
    LibroDTO guardar(LibroDTO dto);
    void eliminar(Long id);
}
