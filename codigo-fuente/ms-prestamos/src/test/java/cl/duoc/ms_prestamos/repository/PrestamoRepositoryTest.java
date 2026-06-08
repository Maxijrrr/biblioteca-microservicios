package cl.duoc.ms_prestamos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.ms_prestamos.model.Prestamo;

@DataJpaTest
class PrestamoRepositoryTest {

    @Autowired
    private PrestamoRepository repository;

    @Test
    @DisplayName("Debe guardar prestamo en H2")
    void debeGuardarPrestamoEnH2() {
        Prestamo guardado = repository.save(crearPrestamo(10L, "978-save", "ACTIVO", 7));

        assertTrue(repository.findById(guardado.getId()).isPresent());
        assertEquals("978-save", repository.findById(guardado.getId()).orElseThrow().getIsbn());
    }

    @Test
    @DisplayName("Debe buscar prestamos por perfil")
    void debeBuscarPrestamosPorPerfil() {
        repository.save(crearPrestamo(20L, "978-a", "ACTIVO", 7));
        repository.save(crearPrestamo(20L, "978-b", "DEVUELTO", 8));
        repository.save(crearPrestamo(21L, "978-c", "ACTIVO", 7));

        List<Prestamo> resultado = repository.findByIdPerfil(20L);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(prestamo -> prestamo.getIdPerfil().equals(20L)));
    }

    @Test
    @DisplayName("Debe buscar prestamos vencidos activos")
    void debeBuscarPrestamosVencidosActivos() {
        LocalDateTime ahora = LocalDateTime.of(2026, 6, 8, 12, 0);
        repository.save(crearPrestamo(30L, "978-vencido", "ACTIVO", -1));
        repository.save(crearPrestamo(31L, "978-futuro", "ACTIVO", 3));
        repository.save(crearPrestamo(32L, "978-devuelto", "DEVUELTO", -2));

        List<Prestamo> resultado = repository.findByEstadoAndFechaDevolucionEsperadaBefore("ACTIVO", ahora);

        assertEquals(1, resultado.size());
        assertEquals("978-vencido", resultado.get(0).getIsbn());
    }

    @Test
    @DisplayName("Debe buscar prestamos por ISBN")
    void debeBuscarPrestamosPorIsbn() {
        repository.save(crearPrestamo(40L, "978-isbn", "ACTIVO", 7));
        repository.save(crearPrestamo(41L, "978-isbn", "DEVUELTO", 7));
        repository.save(crearPrestamo(42L, "978-other", "ACTIVO", 7));

        List<Prestamo> resultado = repository.findByIsbn("978-isbn");

        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    private Prestamo crearPrestamo(Long idPerfil, String isbn, String estado, int diasDevolucion) {
        LocalDateTime base = LocalDateTime.of(2026, 6, 8, 12, 0);
        return new Prestamo(null, idPerfil, isbn, base.minusDays(1), base.plusDays(diasDevolucion), estado);
    }
}
