package cl.duoc.ms_autenticador.repository;

import cl.duoc.ms_autenticador.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void debeGuardarUsuarioEnH2() {
        // Arrange
        Usuario usuario = crearUsuario("maxi");

        // Act
        Usuario guardado = usuarioRepository.save(usuario);

        // Assert
        assertNotNull(guardado.getId());
        assertEquals("maxi", guardado.getUsername());
    }

    @Test
    void debeBuscarUsuarioPorId() {
        // Arrange
        Usuario guardado = usuarioRepository.save(crearUsuario("genesis"));

        // Act
        Optional<Usuario> resultado = usuarioRepository.findById(guardado.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("genesis", resultado.get().getUsername());
    }

    @Test
    void debeListarUsuariosRegistrados() {
        // Arrange
        usuarioRepository.save(crearUsuario("vicente"));
        usuarioRepository.save(crearUsuario("maximiliano"));

        // Act
        List<Usuario> resultado = usuarioRepository.findAll();

        // Assert
        assertEquals(2, resultado.size());
    }

    @Test
    void debeBuscarUsuarioPorUsername() {
        // Arrange
        usuarioRepository.save(crearUsuario("bibliotecario"));

        // Act
        Optional<Usuario> resultado = usuarioRepository.findByUsername("bibliotecario");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("ADMIN", resultado.get().getRol());
    }

    private Usuario crearUsuario(String username) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword("clave-segura");
        usuario.setRol("ADMIN");
        return usuario;
    }
}
