package cl.duoc.ms_autenticador.service;

import cl.duoc.ms_autenticador.dto.UsuarioDTO;
import cl.duoc.ms_autenticador.model.Usuario;
import cl.duoc.ms_autenticador.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testLoginExitoso() {
        // Arrange
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("maxi");
        usuarioMock.setPassword("123456");
        usuarioMock.setRol("ALUMNO");

        when(usuarioRepository.findByUsername("maxi")).thenReturn(Optional.of(usuarioMock));

        // Act
        Usuario resultado = usuarioService.login("maxi", "123456");

        // Assert
        assertNotNull(resultado);
        assertEquals("maxi", resultado.getUsername());
        verify(usuarioRepository, times(1)).findByUsername("maxi");
    }

    @Test
    void testLoginFallidoLanzaRuntimeException() {
        // Arrange
        when(usuarioRepository.findByUsername("maxi")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            usuarioService.login("maxi", "clave_incorrecta");
        });
    }

    @Test
    void testRegistrarUsuarioExitoso() {
        // Arrange
        Usuario nuevo = new Usuario();
        nuevo.setUsername("nuevo_alumno");
        nuevo.setPassword("focusforte");

        when(usuarioRepository.findByUsername("nuevo_alumno")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevo);

        // Act
        Usuario resultado = usuarioService.registrarUsuario(nuevo);

        // Assert
        assertNotNull(resultado);
        assertEquals("nuevo_alumno", resultado.getUsername());
    }

    @Test
    void testRegistrarUsuarioDuplicadoLanzaException() {
        // Arrange
        Usuario duplicado = new Usuario();
        duplicado.setUsername("maxi");

        when(usuarioRepository.findByUsername("maxi")).thenReturn(Optional.of(duplicado));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(duplicado);
        });
    }

    @Test
    void testEsAdminTrue() {
        // Arrange
        Usuario adminMock = new Usuario();
        adminMock.setUsername("bibliotecario");
        adminMock.setRol("ADMIN");

        when(usuarioRepository.findByUsername("bibliotecario")).thenReturn(Optional.of(adminMock));

        // Act
        boolean resultado = usuarioService.esAdmin("bibliotecario");

        // Assert
        assertTrue(resultado);
    }

    @Test
    void testObtenerPerfilExitoso() {
        // Arrange
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(2L);
        usuarioMock.setUsername("pumanque");
        usuarioMock.setRol("ALUMNO");

        when(usuarioRepository.findByUsername("pumanque")).thenReturn(Optional.of(usuarioMock));

        // Act
        Optional<UsuarioDTO> resultado = usuarioService.obtenerPerfil("pumanque");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("pumanque", resultado.get().getUsername());
        assertEquals(2L, resultado.get().getId());
    }
}
