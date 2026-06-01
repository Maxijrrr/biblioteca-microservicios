package cl.duoc.ms_autenticador.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import cl.duoc.ms_autenticador.dto.UsuarioDTO;
import cl.duoc.ms_autenticador.dto.LoginRequest;
import cl.duoc.ms_autenticador.model.Usuario;
import cl.duoc.ms_autenticador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticación", description = "Endpoints para el manejo de sesiones de usuarios y validación de tokens")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;



    @Operation(
        summary = "Registrar un nuevo usuario", 
        description = "Crea una nueva cuenta de usuario en el sistema con las credenciales entregadas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o el usuario ya existe"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })
    // Endpoint para crear usuarios nuevos
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(mapearADto(nuevoUsuario));
    }

    @Operation(
        summary = "Iniciar sesión de usuario", 
        description = "Autentica al usuario con sus credenciales y retorna un token JWT válido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, retorna el token JWT"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas (Usuario o contraseña inválida)"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })

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
    @Operation(
        summary = "Buscar usuario por su Username", 
        description = "Obtiene los detalles del perfil de un usuario específico utilizando su nombre de usuario. Requiere pasar el Token JWT en la cabecera.",
        security = @SecurityRequirement(name = "bearerAuth") // <-- Esto activa el candado individual en Swagger
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado, el token es inválido, expiró o no fue enviado"),
        @ApiResponse(responseCode = "404", description = "El nombre de usuario (username) buscado no existe en el sistema"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<?> verPerfil(@PathVariable String username) {
        // Buscamos el perfil en el service
        java.util.Optional<UsuarioDTO> perfilOpt = usuarioService.obtenerPerfil(username);

        if (perfilOpt.isPresent()) {
            return ResponseEntity.ok(perfilOpt.get());
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    @Operation(
        summary = "Actualizar contraseña de usuario", 
        description = "Modifica la contraseña del usuario indicado en la ruta. Requiere autenticación Bearer Token.",
        security = @SecurityRequirement(name = "bearerAuth") // <-- Agrega el candado individual
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado, token inválido o ausente"),
        @ApiResponse(responseCode = "404", description = "El usuario especificado no existe"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })

    // Nuevo: Cambiar contraseña
    @PatchMapping("/users/{username}/password")
    public ResponseEntity<String> cambiarPass(@RequestParam String username, @RequestParam String newPass) {
        try {
            usuarioService.actualizarPassword(username, newPass);
            return ResponseEntity.ok("Contraseña actualizada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}




