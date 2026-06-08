package cl.duoc.ms_reservas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_reservas.dto.ReservaDTO;
import cl.duoc.ms_reservas.exception.RecursoNoEncontradoException;
import cl.duoc.ms_reservas.model.Reserva;
import cl.duoc.ms_reservas.repository.ReservaRepository;
import cl.duoc.ms_reservas.service.impl.ReservaServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository repository;

    @InjectMocks
    private ReservaServiceImpl service;

    @Test
    @DisplayName("Debe listar reservas como DTO")
    void debeListarReservasComoDto() {
        when(repository.findAll()).thenReturn(List.of(new Reserva(1L, 10L, "978-list", 1)));

        List<ReservaDTO> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals("978-list", resultado.get(0).getIsbn());
    }

    @Test
    @DisplayName("Debe obtener reserva por ID cuando existe")
    void debeObtenerReservaPorIdCuandoExiste() {
        when(repository.findById(2L)).thenReturn(Optional.of(new Reserva(2L, 20L, "978-id", 2)));

        ReservaDTO resultado = service.obtenerPorId(2L);

        assertEquals(2L, resultado.getIdReserva());
        assertEquals(20L, resultado.getIdPerfil());
    }

    @Test
    @DisplayName("Debe lanzar 404 cuando la reserva no existe")
    void debeLanzarNotFoundCuandoReservaNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe guardar reserva desde DTO")
    void debeGuardarReservaDesdeDto() {
        ReservaDTO request = crearDto(null, 30L, "978-save", 1);
        when(repository.save(any(Reserva.class))).thenReturn(new Reserva(7L, 30L, "978-save", 1));

        ReservaDTO resultado = service.guardar(request);

        assertEquals(7L, resultado.getIdReserva());
        assertEquals("978-save", resultado.getIsbn());
    }

    @Test
    @DisplayName("Debe eliminar reserva existente")
    void debeEliminarReservaExistente() {
        when(repository.existsById(4L)).thenReturn(true);

        service.eliminar(4L);

        verify(repository).deleteById(4L);
    }

    @Test
    @DisplayName("Debe rechazar eliminacion de reserva inexistente")
    void debeRechazarEliminacionDeReservaInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(99L));
    }

    private ReservaDTO crearDto(Long id, Long idPerfil, String isbn, Integer prioridad) {
        ReservaDTO dto = new ReservaDTO();
        dto.setIdReserva(id);
        dto.setIdPerfil(idPerfil);
        dto.setIsbn(isbn);
        dto.setPrioridad(prioridad);
        return dto;
    }
}
