package cl.duoc.ms_devoluciones.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DevolucionTest {

    @Test
    @DisplayName("Debe crear devolucion con constructor vacio y setters")
    void debeCrearDevolucionConConstructorVacioYSetters() {
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 8, 10, 30);
        Devolucion devolucion = new Devolucion();

        devolucion.setIdDevolucion(1L);
        devolucion.setIdPrestamo(10L);
        devolucion.setFechaEntrega(fecha);

        assertEquals(1L, devolucion.getIdDevolucion());
        assertEquals(10L, devolucion.getIdPrestamo());
        assertEquals(fecha, devolucion.getFechaEntrega());
    }

    @Test
    @DisplayName("Debe crear devolucion con constructor completo")
    void debeCrearDevolucionConConstructorCompleto() {
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 8, 11, 0);
        Devolucion devolucion = new Devolucion(2L, 20L, fecha);

        assertNotNull(devolucion);
        assertEquals(2L, devolucion.getIdDevolucion());
        assertEquals(20L, devolucion.getIdPrestamo());
    }

    @Test
    @DisplayName("Debe comparar devoluciones con equals y hashCode")
    void debeCompararDevolucionesConEqualsYHashCode() {
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 8, 12, 0);
        Devolucion devolucionA = new Devolucion(3L, 30L, fecha);
        Devolucion devolucionB = new Devolucion(3L, 30L, fecha);
        Devolucion devolucionC = new Devolucion(4L, 40L, fecha.plusDays(1));

        assertEquals(devolucionA, devolucionB);
        assertEquals(devolucionA.hashCode(), devolucionB.hashCode());
        assertNotEquals(devolucionA, devolucionC);
    }
}
