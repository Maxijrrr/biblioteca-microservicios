package cl.duoc.ms_sucursales.controller;

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

import cl.duoc.ms_sucursales.dto.SucursalDTO;
import cl.duoc.ms_sucursales.exception.GlobalExceptionHandler;
import cl.duoc.ms_sucursales.exception.RecursoNoEncontradoException;
import cl.duoc.ms_sucursales.service.SucursalService;

@ExtendWith(MockitoExtension.class)
class SucursalControllerTest {

    @Mock
    private SucursalService service;

    @InjectMocks
    private SucursalController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator())
                .build();
    }

    @Test
    @DisplayName("GET debe responder 200 con lista de sucursales")
    void listarTodasDebeResponderOk() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(crearDto(1L, "Sucursal Lista", "Direccion Lista")));

        mockMvc.perform(get("/api/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSede").value("Sucursal Lista"));
    }

    @Test
    @DisplayName("GET por ID debe responder 404 si no existe")
    void obtenerPorIdDebeResponderNotFound() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Sucursal no encontrada"));

        mockMvc.perform(get("/api/sucursales/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Sucursal no encontrada"));
    }

    @Test
    @DisplayName("POST debe responder 201 al crear sucursal valida")
    void crearDebeResponderCreated() throws Exception {
        when(service.guardar(any(SucursalDTO.class))).thenReturn(crearDto(5L, "Sucursal Creada", "Direccion Creada"));

        mockMvc.perform(post("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreSede": "Sucursal Creada",
                                  "direccion": "Direccion Creada"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSucursal").value(5))
                .andExpect(jsonPath("$.nombreSede").value("Sucursal Creada"));
    }

    @Test
    @DisplayName("POST debe responder 400 con DTO invalido")
    void crearDebeResponderBadRequestConDtoInvalido() throws Exception {
        mockMvc.perform(post("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombreSede": "",
                                  "direccion": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreSede").value("El nombre de la sede es obligatorio"))
                .andExpect(jsonPath("$.direccion").exists());
    }

    private SucursalDTO crearDto(Long id, String nombre, String direccion) {
        SucursalDTO dto = new SucursalDTO();
        dto.setIdSucursal(id);
        dto.setNombreSede(nombre);
        dto.setDireccion(direccion);
        return dto;
    }

    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
