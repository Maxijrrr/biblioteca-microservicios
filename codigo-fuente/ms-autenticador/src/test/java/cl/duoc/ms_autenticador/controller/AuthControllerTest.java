package cl.duoc.ms_autenticador.controller;

import cl.duoc.ms_autenticador.model.Usuario;
import cl.duoc.ms_autenticador.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        // Le agregamos el ControllerAdvice (tu manejador global) al entorno de pruebas aislado
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new cl.duoc.ms_autenticador.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    void testLoginExitosoRetorna200() throws Exception {
        // Arrange
        Usuario usuarioMock = new Usuario();
        usuarioMock.setUsername("maxi");
        usuarioMock.setRol("ALUMNO");
        
        when(usuarioService.login("maxi", "123")).thenReturn(usuarioMock);
        String jsonRequest = "{\"username\":\"maxi\",\"password\":\"123\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Acceso concedido: Bienvenido Alumno maxi"));
    }

    @Test
    void testLoginFallidoRetorna401() throws Exception {
        // Arrange
        when(usuarioService.login("user_malo", "pass_mala"))
                .thenThrow(new RuntimeException("Usuario o contraseña incorrectos."));
                
        String jsonRequest = "{\"username\":\"user_malo\",\"password\":\"pass_mala\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testVerPerfilNoEncontradoRetorna404() throws Exception {
        // Arrange
        when(usuarioService.obtenerPerfil("desconocido")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/auth/users/desconocido"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void testRegistrarUsuarioRetorna200o201() throws Exception {
        // Arrange
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setUsername("nuevo_user");
        usuarioEntrada.setPassword("pass123");

        // Tu controlador mapea a un DTO, así que simulamos que el service devuelve el usuario listo
        when(usuarioService.registrarUsuario(any(Usuario.class))).thenReturn(usuarioEntrada);
        
        String jsonRequest = "{\"username\":\"nuevo_user\",\"password\":\"pass123\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk()); // Tu controlador responde .ok() en /registrar, lo cual cumple la lógica de éxito
    }
}