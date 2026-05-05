package cl.duoc.ms_autenticador.service;

import cl.duoc.ms_autenticador.model.Usuario;
import cl.duoc.ms_autenticador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import cl.duoc.ms_autenticador.dto.UsuarioDTO;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. MÉTODO PARA REGISTRAR (Con validación de duplicados)
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("Error: El nombre de usuario ya existe.");
        }
        return usuarioRepository.save(usuario);
    }

    // 2. MÉTODO PARA LOGIN (Valida credenciales)
    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos."));
    }
    //  validar si es ADMIN
    public boolean esAdmin(String username) {
        return usuarioRepository.findByUsername(username)
                .map(u -> u.getRol().equalsIgnoreCase("ADMIN"))
                .orElse(false);
    }

    // actualizar contraseña
    public void actualizarPassword(String username, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPassword(nuevaPassword); // En producción aquí va el encoder
        usuarioRepository.save(usuario);
    }

    // obtener perfil seguro (usa tu UsuarioDTO)
    public Optional<UsuarioDTO> obtenerPerfil(String username) {
        return usuarioRepository.findByUsername(username)
                .map(u -> new UsuarioDTO(u.getId(), u.getUsername(), u.getRol()));
    }
}
