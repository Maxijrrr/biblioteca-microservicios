package cl.duoc.ms_autenticador.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testConstructorVacioYSetters() {
        // Arrange (AAA)
        Usuario usuario = new Usuario();

        // Act
        usuario.setId(1L);
        usuario.setUsername("maxi");
        usuario.setPassword("fortefocus");
        usuario.setRol("ALUMNO");

        // Assert
        assertEquals(1L, usuario.getId());
        assertEquals("maxi", usuario.getUsername());
        assertEquals("fortefocus", usuario.getPassword());
        assertEquals("ALUMNO", usuario.getRol());
    }

    @Test
    void testToStringYEquals() {
        // Arrange
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setUsername("maxi");

        Usuario u2 = new Usuario();
        u2.setId(1L);
        u2.setUsername("maxi");

        // Assert
        assertEquals(u1, u2);
        assertNotNull(u1.toString());
    }

    @Test
    void testHashCode() {
        // Arrange
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setUsername("maxi");

        Usuario u2 = new Usuario();
        u2.setId(1L);
        u2.setUsername("maxi");

        // Assert
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}