package cl.duoc.ms_prestamos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import cl.duoc.ms_prestamos.dto.PrestamoDTO;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;
import cl.duoc.ms_prestamos.exception.GlobalExceptionHandler;
import cl.duoc.ms_prestamos.exception.RecursoNoEncontradoException;
import cl.duoc.ms_prestamos.service.PrestamoService;

@ExtendWith(MockitoExtension.class)
class PrestamoControllerTest {

    @Mock
    private PrestamoService service;

    @InjectMocks
    private PrestamoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator())
                .build();
    }

    @Test
    @DisplayName("POST debe responder 201 al solicitar prestamo valido")
    void solicitarDebeResponderCreated() throws Exception {
        when(service.crearPrestamo(any(PrestamoDTO.class))).thenReturn(crearResponse(5L, 10L, "978-create", "ACTIVO"));

        mockMvc.perform(post("/api/v1/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPerfil": 10,
                                  "isbn": "978-create"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPrestamo").value(5))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    @DisplayName("POST debe responder 400 con DTO invalido")
    void solicitarDebeResponderBadRequestConDtoInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPerfil": null,
                                  "isbn": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idPerfil").value("El ID de perfil es obligatorio"))
                .andExpect(jsonPath("$.isbn").value("El ISBN es obligatorio"));
    }

    @Test
    @DisplayName("GET debe responder 200 con lista de prestamos")
    void listarTodosDebeResponderOk() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(crearResponse(1L, 11L, "978-list", "ACTIVO")));

        mockMvc.perform(get("/api/v1/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("978-list"));
    }

    @Test
    @DisplayName("GET por ID debe responder 404 si no existe")
    void obtenerPorIdDebeResponderNotFound() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Prestamo no encontrado"));

        mockMvc.perform(get("/api/v1/prestamos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Prestamo no encontrado"));
    }

    @Test
    @DisplayName("PATCH renovar debe responder 200 con prestamo actualizado")
    void renovarDebeResponderOk() throws Exception {
        when(service.renovarPrestamo(7L)).thenReturn(crearResponse(7L, 12L, "978-renovar", "ACTIVO"));

        mockMvc.perform(patch("/api/v1/prestamos/renovar/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPrestamo").value(7))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    private PrestamoResponseDTO crearResponse(Long id, Long idPerfil, String isbn, String estado) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setIdPrestamo(id);
        dto.setIdPerfil(idPerfil);
        dto.setIsbn(isbn);
        dto.setFechaPrestamo(LocalDateTime.of(2026, 6, 8, 12, 0));
        dto.setFechaDevolucionEsperada(LocalDateTime.of(2026, 6, 15, 12, 0));
        dto.setEstado(estado);
        return dto;
    }

    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
