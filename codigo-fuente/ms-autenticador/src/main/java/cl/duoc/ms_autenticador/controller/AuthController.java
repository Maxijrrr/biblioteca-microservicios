package cl.duoc.ms_autenticador.controller;

import cl.duoc.ms_autenticador.dto.UsuarioDTO;
import cl.duoc.ms_autenticador.dto.LoginRequest;
import cl.duoc.ms_autenticador.model.Usuario;
import cl.duoc.ms_autenticador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para crear usuarios nuevos
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(mapearADto(nuevoUsuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Obtenemos el objeto Usuario del service
        Usuario usuario = usuarioService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (usuario != null) {
            String rol = usuario.getRol();
            String mensaje = rol.equalsIgnoreCase("ADMIN") ? "Panel de Control: Bienvenido Bibliotecario " : "Acceso concedido: Bienvenido Alumno ";
            return ResponseEntity.ok(mensaje + usuario.getUsername());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    } 

    // Método privado para convertir Usuario a DTO (Seguridad)
    private UsuarioDTO mapearADto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRol());
        return dto;
    }
    @GetMapping("/perfil/{username}")
    public ResponseEntity<?> verPerfil(@PathVariable String username) {
        // Buscamos el perfil en el service
        java.util.Optional<UsuarioDTO> perfilOpt = usuarioService.obtenerPerfil(username);

        if (perfilOpt.isPresent()) {
            return ResponseEntity.ok(perfilOpt.get());
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    // Nuevo: Cambiar contraseña
    @PatchMapping("/update-password")
    public ResponseEntity<String> cambiarPass(@RequestParam String username, @RequestParam String newPass) {
        try {
            usuarioService.actualizarPassword(username, newPass);
            return ResponseEntity.ok("Contraseña actualizada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}




