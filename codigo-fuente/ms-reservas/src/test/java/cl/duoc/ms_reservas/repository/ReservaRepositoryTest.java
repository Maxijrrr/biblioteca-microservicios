package cl.duoc.ms_reservas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.ms_reservas.model.Reserva;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository repository;

    @Test
    @DisplayName("Debe guardar una reserva en H2")
    void debeGuardarReserva() {
        Reserva reserva = new Reserva(null, 1L, "978-save", 1);

        Reserva guardada = repository.save(reserva);

        assertTrue(guardada.getIdReserva() > 0);
        assertEquals("978-save", guardada.getIsbn());
    }

    @Test
    @DisplayName("Debe buscar reserva por ID")
    void debeBuscarReservaPorId() {
        Reserva guardada = repository.save(new Reserva(null, 2L, "978-id", 2));

        Optional<Reserva> encontrada = repository.findById(guardada.getIdReserva());

        assertTrue(encontrada.isPresent());
        assertEquals(2L, encontrada.get().getIdPerfil());
    }

    @Test
    @DisplayName("Debe listar reservas registradas")
    void debeListarReservasRegistradas() {
        repository.save(new Reserva(null, 1L, "978-list-1", 1));
        repository.save(new Reserva(null, 2L, "978-list-2", 2));

        List<Reserva> reservas = repository.findAll();

        assertEquals(2, reservas.size());
    }
}
