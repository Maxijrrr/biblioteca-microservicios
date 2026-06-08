package cl.duoc.ms_catalogo.controller;

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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import cl.duoc.ms_catalogo.exception.GlobalExceptionHandler;
import cl.duoc.ms_catalogo.exception.RecursoNoEncontradoException;
import cl.duoc.ms_catalogo.service.LibroService;

@ExtendWith(MockitoExtension.class)
class LibroControllerTest {

    @Mock
    private LibroService service;

    @InjectMocks
    private LibroController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator())
                .build();
    }

    @Test
    @DisplayName("GET debe responder 200 con lista de libros")
    void listarTodosDebeResponderOk() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(crearDto(1L, "978-list", "Libro Lista")));

        mockMvc.perform(get("/api/catalogo/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Libro Lista"));
    }

    @Test
    @DisplayName("GET por ID debe responder 404 si no existe")
    void obtenerPorIdDebeResponderNotFound() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new RecursoNoEncontradoException("Libro no encontrado"));

        mockMvc.perform(get("/api/catalogo/libros/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Libro no encontrado"));
    }

    @Test
    @DisplayName("POST debe responder 201 al crear libro valido")
    void crearDebeResponderCreated() throws Exception {
        when(service.guardar(any(LibroDTO.class))).thenReturn(crearDto(5L, "978-create", "Libro Creado"));

        mockMvc.perform(post("/api/catalogo/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "isbn": "978-create",
                                  "titulo": "Libro Creado",
                                  "autor": "Autor Demo",
                                  "editorial": "Editorial Demo",
                                  "anioPublicacion": 2026,
                                  "categoria": "Biblioteca"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idLibro").value(5))
                .andExpect(jsonPath("$.titulo").value("Libro Creado"));
    }

    @Test
    @DisplayName("POST debe responder 400 con DTO invalido")
    void crearDebeResponderBadRequestConDtoInvalido() throws Exception {
        mockMvc.perform(post("/api/catalogo/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "isbn": "",
                                  "titulo": "",
                                  "autor": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isbn").value("El ISBN no puede estar vacio"))
                .andExpect(jsonPath("$.titulo").value("El titulo no puede estar vacio"));
    }

    private LibroDTO crearDto(Long id, String isbn, String titulo) {
        LibroDTO dto = new LibroDTO();
        dto.setIdLibro(id);
        dto.setIsbn(isbn);
        dto.setTitulo(titulo);
        dto.setAutor("Autor Demo");
        dto.setEditorial("Editorial Demo");
        dto.setAnioPublicacion(2026);
        dto.setCategoria("Biblioteca");
        return dto;
    }

    private LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }
}
