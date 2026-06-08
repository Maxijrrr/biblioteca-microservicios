package cl.duoc.ms_catalogo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.ms_catalogo.model.Libro;

@DataJpaTest
class LibroRepositoryTest {

    @Autowired
    private LibroRepository repository;

    @Test
    @DisplayName("Debe guardar un libro en H2")
    void debeGuardarLibro() {
        Libro libro = crearLibro(null, "978-save", "Libro Guardado");

        Libro guardado = repository.save(libro);

        assertTrue(guardado.getIdLibro() > 0);
        assertEquals("978-save", guardado.getIsbn());
    }

    @Test
    @DisplayName("Debe buscar libro por ID")
    void debeBuscarLibroPorId() {
        Libro guardado = repository.save(crearLibro(null, "978-id", "Libro ID"));

        Optional<Libro> encontrado = repository.findById(guardado.getIdLibro());

        assertTrue(encontrado.isPresent());
        assertEquals("Libro ID", encontrado.get().getTitulo());
    }

    @Test
    @DisplayName("Debe listar libros registrados")
    void debeListarLibrosRegistrados() {
        repository.save(crearLibro(null, "978-list-1", "Libro Uno"));
        repository.save(crearLibro(null, "978-list-2", "Libro Dos"));

        List<Libro> libros = repository.findAll();

        assertEquals(2, libros.size());
    }

    @Test
    @DisplayName("Debe buscar libro por ISBN")
    void debeBuscarLibroPorIsbn() {
        repository.save(crearLibro(null, "978-isbn", "Libro ISBN"));

        Optional<Libro> encontrado = repository.findByIsbn("978-isbn");

        assertTrue(encontrado.isPresent());
        assertEquals("Libro ISBN", encontrado.get().getTitulo());
    }

    private Libro crearLibro(Long id, String isbn, String titulo) {
        return new Libro(id, isbn, titulo, "Autor Demo", "Editorial Demo", 2026, "Biblioteca");
    }
}
