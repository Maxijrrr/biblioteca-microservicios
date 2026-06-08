package cl.duoc.ms_devoluciones.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.ms_devoluciones.model.Devolucion;

@DataJpaTest
class DevolucionRepositoryTest {

    @Autowired
    private DevolucionRepository repository;

    @Test
    @DisplayName("Debe guardar una devolucion en H2")
    void debeGuardarDevolucion() {
        Devolucion devolucion = new Devolucion(null, 1L, LocalDateTime.now());

        Devolucion guardada = repository.save(devolucion);

        assertTrue(guardada.getIdDevolucion() > 0);
        assertEquals(1L, guardada.getIdPrestamo());
    }

    @Test
    @DisplayName("Debe buscar devolucion por ID")
    void debeBuscarDevolucionPorId() {
        Devolucion guardada = repository.save(new Devolucion(null, 2L, LocalDateTime.now()));

        Optional<Devolucion> encontrada = repository.findById(guardada.getIdDevolucion());

        assertTrue(encontrada.isPresent());
        assertEquals(2L, encontrada.get().getIdPrestamo());
    }

    @Test
    @DisplayName("Debe listar devoluciones registradas")
    void debeListarDevolucionesRegistradas() {
        repository.save(new Devolucion(null, 1L, LocalDateTime.now()));
        repository.save(new Devolucion(null, 2L, LocalDateTime.now()));

        List<Devolucion> devoluciones = repository.findAll();

        assertEquals(2, devoluciones.size());
    }
}
