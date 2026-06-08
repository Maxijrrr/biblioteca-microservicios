package cl.duoc.ms_devoluciones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.exception.GlobalExceptionHandler;
import cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException;
import cl.duoc.ms_devoluciones.service.DevolucionService;

@ExtendWith(MockitoExtension.class)
class DevolucionControllerTest {

    @Mock
    private DevolucionService service;

    @InjectMocks
    private DevolucionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator())
                .build();
    }

    @Test
    @DisplayName("GET debe responder 200 con lista de devoluciones")
    void listarTodasDebeResponderOk() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(crearDto(1L, 10L)));

        mockMvc.perform(get("/api/devoluciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPrestamo").value(10));
    }

    @Test
    @DisplayName("GET por ID debe responder 404 si no existe")
    void obtenerPorIdDebeResponderNotFound() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Devolucion no encontrada"));

        mockMvc.perform(get("/api/devoluciones/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Devolucion no encontrada"));
    }

    @Test
    @DisplayName("POST debe responder 201 al crear devolucion valida")
    void crearDebeResponderCreated() throws Exception {
        when(service.guardar(any(DevolucionDTO.class))).thenReturn(crearDto(5L, 20L));

        mockMvc.perform(post("/api/devoluciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPrestamo": 20
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDevolucion").value(5))
                .andExpect(jsonPath("$.idPrestamo").value(20));
    }

    @Test
    @DisplayName("POST debe responder 400 con DTO invalido")
    void crearDebeResponderBadRequestConDtoInvalido() throws Exception {
        mockMvc.perform(post("/api/devoluciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPrestamo": null
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idPrestamo").exists());
    }

    private DevolucionDTO crearDto(Long id, Long idPrestamo) {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdDevolucion(id);
        dto.setIdPrestamo(idPrestamo);
        dto.setFechaEntrega(LocalDateTime.of(2026, 6, 8, 10, 0));
        return dto;
    }

    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
