package cl.duoc.ms_perfiles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_perfiles.dto.PerfilResponseDTO;
import cl.duoc.ms_perfiles.exception.NotFoundException;
import cl.duoc.ms_perfiles.model.PerfilEntity;
import cl.duoc.ms_perfiles.repository.PerfilRepository;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

    @Test
    void buscarPerfilPorIdDevuelveDtoCuandoExiste() {
        PerfilEntity perfil = PerfilEntity.builder()
                .idPerfil(7L)
                .rut("11111111-1")
                .nombre("Ada Lovelace")
                .correo("ada@duoc.cl")
                .carrera("Informatica")
                .build();

        when(perfilRepository.findById(7L)).thenReturn(Optional.of(perfil));

        PerfilResponseDTO dto = perfilService.buscarPerfilPorId(7L);

        assertEquals(7L, dto.id());
        assertEquals("11111111-1", dto.rut());
        assertEquals("Ada Lovelace", dto.nombre());
        assertEquals("ada@duoc.cl", dto.correo());
        assertEquals("Informatica", dto.carrera());
    }

    @Test
    void buscarPerfilPorIdLanzaNotFoundCuandoNoExiste() {
        when(perfilRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> perfilService.buscarPerfilPorId(99L));
    }

    @Test
    void listarTodosDevuelvePerfilesComoDto() {
        PerfilEntity perfil = PerfilEntity.builder()
                .idPerfil(1L)
                .rut("12345678-9")
                .nombre("Juan Perez")
                .correo("juan@duoc.cl")
                .carrera("Informatica")
                .build();

        when(perfilRepository.findAll()).thenReturn(List.of(perfil));

        List<PerfilResponseDTO> resultado = perfilService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).nombre());
    }
}
