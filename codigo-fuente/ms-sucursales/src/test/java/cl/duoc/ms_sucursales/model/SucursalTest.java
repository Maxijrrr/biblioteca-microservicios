package cl.duoc.ms_sucursales.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SucursalTest {

    @Test
    @DisplayName("Debe crear sucursal con constructor vacio y setters")
    void debeCrearSucursalConConstructorVacioYSetters() {
        Sucursal sucursal = new Sucursal();

        sucursal.setIdSucursal(1L);
        sucursal.setNombreSede("Biblioteca Central");
        sucursal.setDireccion("Av. Principal 123");

        assertEquals(1L, sucursal.getIdSucursal());
        assertEquals("Biblioteca Central", sucursal.getNombreSede());
        assertEquals("Av. Principal 123", sucursal.getDireccion());
    }

    @Test
    @DisplayName("Debe crear sucursal con constructor completo")
    void debeCrearSucursalConConstructorCompleto() {
        Sucursal sucursal = new Sucursal(2L, "Sede Alameda", "Alameda 1000");

        assertNotNull(sucursal);
        assertEquals(2L, sucursal.getIdSucursal());
        assertEquals("Sede Alameda", sucursal.getNombreSede());
    }

    @Test
    @DisplayName("Debe comparar sucursales con equals y hashCode")
    void debeCompararSucursalesConEqualsYHashCode() {
        Sucursal sucursalA = new Sucursal(3L, "Sede Norte", "Calle Norte 10");
        Sucursal sucursalB = new Sucursal(3L, "Sede Norte", "Calle Norte 10");
        Sucursal sucursalC = new Sucursal(4L, "Sede Sur", "Calle Sur 20");

        assertEquals(sucursalA, sucursalB);
        assertEquals(sucursalA.hashCode(), sucursalB.hashCode());
        assertNotEquals(sucursalA, sucursalC);
    }
}
