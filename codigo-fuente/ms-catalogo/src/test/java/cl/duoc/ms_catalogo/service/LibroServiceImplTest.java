package cl.duoc.ms_catalogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import cl.duoc.ms_catalogo.exception.RecursoNoEncontradoException;
import cl.duoc.ms_catalogo.model.Libro;
import cl.duoc.ms_catalogo.repository.LibroRepository;
import cl.duoc.ms_catalogo.service.impl.LibroServiceImpl;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository repository;

    @InjectMocks
    private LibroServiceImpl service;

    @Test
    @DisplayName("Debe listar libros como DTO")
    void debeListarLibrosComoDto() {
        when(repository.findAll()).thenReturn(List.of(crearLibro(1L, "978-list", "Libro Lista")));

        List<LibroDTO> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Libro Lista", resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("Debe obtener libro por ID cuando existe")
    void debeObtenerLibroPorIdCuandoExiste() {
        when(repository.findById(2L)).thenReturn(Optional.of(crearLibro(2L, "978-id", "Libro ID")));

        LibroDTO resultado = service.obtenerPorId(2L);

        assertEquals(2L, resultado.getIdLibro());
        assertEquals("Libro ID", resultado.getTitulo());
    }

    @Test
    @DisplayName("Debe lanzar 404 cuando el libro no existe")
    void debeLanzarNotFoundCuandoLibroNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe guardar libro desde DTO")
    void debeGuardarLibroDesdeDto() {
        LibroDTO request = crearDto(null, "978-save", "Libro Nuevo");
        when(repository.save(any(Libro.class))).thenReturn(crearLibro(10L, "978-save", "Libro Nuevo"));

        LibroDTO resultado = service.guardar(request);

        assertEquals(10L, resultado.getIdLibro());
        assertEquals("978-save", resultado.getIsbn());
    }

    @Test
    @DisplayName("Debe validar existencia antes de eliminar")
    void debeValidarExistenciaAntesDeEliminar() {
        when(repository.findById(4L)).thenReturn(Optional.of(crearLibro(4L, "978-del", "Libro Delete")));

        service.eliminar(4L);

        verify(repository).deleteById(4L);
    }

    private Libro crearLibro(Long id, String isbn, String titulo) {
        return new Libro(id, isbn, titulo, "Autor Demo", "Editorial Demo", 2026, "Biblioteca");
    }

    private LibroDTO crearDto(Long id, String isbn, String titulo) {
        LibroDTO dto = new LibroDTO();
        dto.setIdLibro(id);
        dto.setIsbn(isbn);
        dto.setTitulo(titulo);
        dto.setAutor("Autor Demo");
        dto.setEditorial("Editorial Demo");
        dto.setAnioPublicacion(2026);
        dto.setCategoria("Biblioteca");
        return dto;
    }
}
