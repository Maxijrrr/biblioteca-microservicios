package cl.duoc.ms_catalogo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LibroTest {

    @Test
    @DisplayName("Debe crear libro con constructor vacio y setters")
    void debeCrearLibroConConstructorVacioYSetters() {
        Libro libro = new Libro();

        libro.setIdLibro(1L);
        libro.setIsbn("978-3-16-148410-0");
        libro.setTitulo("Arquitectura de Software");
        libro.setAutor("Martin Fowler");
        libro.setEditorial("Addison-Wesley");
        libro.setAnioPublicacion(2024);
        libro.setCategoria("Software");

        assertEquals(1L, libro.getIdLibro());
        assertEquals("978-3-16-148410-0", libro.getIsbn());
        assertEquals("Arquitectura de Software", libro.getTitulo());
        assertEquals("Martin Fowler", libro.getAutor());
        assertEquals("Addison-Wesley", libro.getEditorial());
        assertEquals(2024, libro.getAnioPublicacion());
        assertEquals("Software", libro.getCategoria());
    }

    @Test
    @DisplayName("Debe crear libro con constructor completo")
    void debeCrearLibroConConstructorCompleto() {
        Libro libro = new Libro(2L, "978-0-13-235088-4", "Clean Code", "Robert C. Martin", "Prentice Hall", 2008, "Programacion");

        assertNotNull(libro);
        assertEquals(2L, libro.getIdLibro());
        assertEquals("Clean Code", libro.getTitulo());
        assertEquals("Robert C. Martin", libro.getAutor());
    }

    @Test
    @DisplayName("Debe comparar libros con equals y hashCode")
    void debeCompararLibrosConEqualsYHashCode() {
        Libro libroA = new Libro(3L, "978-1", "Domain Driven Design", "Eric Evans", "Addison-Wesley", 2003, "Diseno");
        Libro libroB = new Libro(3L, "978-1", "Domain Driven Design", "Eric Evans", "Addison-Wesley", 2003, "Diseno");
        Libro libroC = new Libro(4L, "978-2", "Refactoring", "Martin Fowler", "Addison-Wesley", 2018, "Diseno");

        assertEquals(libroA, libroB);
        assertEquals(libroA.hashCode(), libroB.hashCode());
        assertNotEquals(libroA, libroC);
    }
}
