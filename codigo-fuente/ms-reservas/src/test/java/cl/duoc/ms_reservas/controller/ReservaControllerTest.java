package cl.duoc.ms_reservas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import cl.duoc.ms_reservas.dto.ReservaDTO;
import cl.duoc.ms_reservas.exception.GlobalExceptionHandler;
import cl.duoc.ms_reservas.exception.RecursoNoEncontradoException;
import cl.duoc.ms_reservas.service.ReservaService;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService service;

    @InjectMocks
    private ReservaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator())
                .build();
    }

    @Test
    @DisplayName("GET debe responder 200 con lista de reservas")
    void listarTodasDebeResponderOk() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(crearDto(1L, 10L, "978-list", 1)));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("978-list"));
    }

    @Test
    @DisplayName("GET por ID debe responder 404 si no existe")
    void obtenerPorIdDebeResponderNotFound() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Reserva no encontrada"));

        mockMvc.perform(get("/api/reservas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Reserva no encontrada"));
    }

    @Test
    @DisplayName("POST debe responder 201 al crear reserva valida")
    void crearDebeResponderCreated() throws Exception {
        when(service.guardar(any(ReservaDTO.class))).thenReturn(crearDto(5L, 10L, "978-create", 1));

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPerfil": 10,
                                  "isbn": "978-create",
                                  "prioridad": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReserva").value(5))
                .andExpect(jsonPath("$.isbn").value("978-create"));
    }

    @Test
    @DisplayName("POST debe responder 400 con DTO invalido")
    void crearDebeResponderBadRequestConDtoInvalido() throws Exception {
        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "idPerfil": null,
                                  "isbn": "",
                                  "prioridad": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idPerfil").value("El ID del perfil es obligatorio"))
                .andExpect(jsonPath("$.isbn").value("El ISBN es obligatorio"))
                .andExpect(jsonPath("$.prioridad").value("La prioridad debe ser mayor o igual a 1"));
    }

    private ReservaDTO crearDto(Long id, Long idPerfil, String isbn, Integer prioridad) {
        ReservaDTO dto = new ReservaDTO();
        dto.setIdReserva(id);
        dto.setIdPerfil(idPerfil);
        dto.setIsbn(isbn);
        dto.setPrioridad(prioridad);
        return dto;
    }

    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
