package cl.duoc.ms_prestamos.service;

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

import cl.duoc.ms_prestamos.exception.RecursoNoEncontradoException;
import cl.duoc.ms_prestamos.model.Prestamo;
import cl.duoc.ms_prestamos.repository.PrestamoRepository;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository repository;

    @InjectMocks
    private PrestamoService service;

    @Test
    void obtenerPorIdDevuelvePrestamoCuandoExiste() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(5L);
        prestamo.setEstado("ACTIVO");

        when(repository.findById(5L)).thenReturn(Optional.of(prestamo));

        PrestamoResponseDTO resultado = service.obtenerPorId(5L);

        assertEquals(5L, resultado.getIdPrestamo());
        assertEquals("ACTIVO", resultado.getEstado());
    }

    @Test
    void obtenerPorIdLanzaNotFoundCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void listarTodosDevuelvePrestamosRegistrados() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);

        when(repository.findAll()).thenReturn(List.of(prestamo));

        List<PrestamoResponseDTO> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdPrestamo());
    }
}
