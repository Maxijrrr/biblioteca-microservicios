package cl.duoc.ms_reservas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservaTest {

    @Test
    @DisplayName("Debe crear reserva con constructor vacio y setters")
    void debeCrearReservaConConstructorVacioYSetters() {
        Reserva reserva = new Reserva();

        reserva.setIdReserva(1L);
        reserva.setIdPerfil(10L);
        reserva.setIsbn("978-reserva");
        reserva.setPrioridad(1);

        assertEquals(1L, reserva.getIdReserva());
        assertEquals(10L, reserva.getIdPerfil());
        assertEquals("978-reserva", reserva.getIsbn());
        assertEquals(1, reserva.getPrioridad());
    }

    @Test
    @DisplayName("Debe crear reserva con constructor completo")
    void debeCrearReservaConConstructorCompleto() {
        Reserva reserva = new Reserva(2L, 20L, "978-full", 2);

        assertNotNull(reserva);
        assertEquals(2L, reserva.getIdReserva());
        assertEquals(20L, reserva.getIdPerfil());
    }

    @Test
    @DisplayName("Debe comparar reservas con equals y hashCode")
    void debeCompararReservasConEqualsYHashCode() {
        Reserva reservaA = new Reserva(3L, 30L, "978-eq", 1);
        Reserva reservaB = new Reserva(3L, 30L, "978-eq", 1);
        Reserva reservaC = new Reserva(4L, 40L, "978-diff", 2);

        assertEquals(reservaA, reservaB);
        assertEquals(reservaA.hashCode(), reservaB.hashCode());
        assertNotEquals(reservaA, reservaC);
    }
}
