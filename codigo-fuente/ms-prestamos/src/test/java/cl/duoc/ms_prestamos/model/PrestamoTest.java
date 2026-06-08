package cl.duoc.ms_prestamos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PrestamoTest {

    @Test
    @DisplayName("Constructor vacio debe permitir asignar datos por setters")
    void constructorVacioDebePermitirSetters() {
        Prestamo prestamo = new Prestamo();
        LocalDateTime fechaPrestamo = LocalDateTime.of(2026, 6, 8, 9, 0);
        LocalDateTime fechaDevolucion = fechaPrestamo.plusDays(7);

        prestamo.setId(1L);
        prestamo.setIdPerfil(10L);
        prestamo.setIsbn("978-prestamo");
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucionEsperada(fechaDevolucion);
        prestamo.setEstado("ACTIVO");

        assertEquals(1L, prestamo.getId());
        assertEquals(10L, prestamo.getIdPerfil());
        assertEquals("978-prestamo", prestamo.getIsbn());
        assertEquals(fechaPrestamo, prestamo.getFechaPrestamo());
        assertEquals(fechaDevolucion, prestamo.getFechaDevolucionEsperada());
        assertEquals("ACTIVO", prestamo.getEstado());
    }

    @Test
    @DisplayName("Constructor completo debe inicializar todos los campos")
    void constructorCompletoDebeInicializarCampos() {
        LocalDateTime fechaPrestamo = LocalDateTime.of(2026, 6, 8, 10, 0);
        LocalDateTime fechaDevolucion = fechaPrestamo.plusDays(7);

        Prestamo prestamo = new Prestamo(2L, 20L, "978-full", fechaPrestamo, fechaDevolucion, "ACTIVO");

        assertEquals(2L, prestamo.getId());
        assertEquals(20L, prestamo.getIdPerfil());
        assertEquals("978-full", prestamo.getIsbn());
        assertEquals(fechaPrestamo, prestamo.getFechaPrestamo());
        assertEquals(fechaDevolucion, prestamo.getFechaDevolucionEsperada());
        assertEquals("ACTIVO", prestamo.getEstado());
    }

    @Test
    @DisplayName("Data de Lombok debe generar equals y hashCode coherentes")
    void dataDebeGenerarEqualsYHashCode() {
        LocalDateTime fechaPrestamo = LocalDateTime.of(2026, 6, 8, 11, 0);
        LocalDateTime fechaDevolucion = fechaPrestamo.plusDays(7);

        Prestamo base = new Prestamo(3L, 30L, "978-equals", fechaPrestamo, fechaDevolucion, "ACTIVO");
        Prestamo copia = new Prestamo(3L, 30L, "978-equals", fechaPrestamo, fechaDevolucion, "ACTIVO");
        Prestamo distinto = new Prestamo(4L, 31L, "978-other", fechaPrestamo, fechaDevolucion, "DEVUELTO");

        assertEquals(base, copia);
        assertEquals(base.hashCode(), copia.hashCode());
        assertNotEquals(base, distinto);
    }

    @Test
    @DisplayName("Constructor vacio deja campos nulos por defecto")
    void constructorVacioDejaCamposNulos() {
        Prestamo prestamo = new Prestamo();

        assertNull(prestamo.getId());
        assertNull(prestamo.getIdPerfil());
        assertNull(prestamo.getIsbn());
        assertNull(prestamo.getEstado());
    }
}
