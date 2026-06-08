package cl.duoc.ms_devoluciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_devoluciones.client.PrestamoClient;
import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.dto.PrestamoDTO;
import cl.duoc.ms_devoluciones.exception.ReglaNegocioException;
import cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException;
import cl.duoc.ms_devoluciones.model.Devolucion;
import cl.duoc.ms_devoluciones.repository.DevolucionRepository;
import cl.duoc.ms_devoluciones.service.impl.DevolucionServiceImpl;

@ExtendWith(MockitoExtension.class)
class DevolucionServiceImplTest {

    @Mock
    private DevolucionRepository repository;

    @Mock
    private PrestamoClient prestamoClient;

    @InjectMocks
    private DevolucionServiceImpl service;

    @Test
    void guardarValidaPrestamoActivoAntesDeRegistrarDevolucion() {
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setEstado("ACTIVO");

        when(prestamoClient.obtenerPrestamo(3L)).thenReturn(prestamo);
        when(repository.save(any())).thenAnswer(invocation -> {
            var devolucion = invocation.getArgument(0, cl.duoc.ms_devoluciones.model.Devolucion.class);
            devolucion.setIdDevolucion(10L);
            return devolucion;
        });

        DevolucionDTO request = new DevolucionDTO();
        request.setIdPrestamo(3L);

        DevolucionDTO resultado = service.guardar(request);

        assertEquals(10L, resultado.getIdDevolucion());
        assertEquals(3L, resultado.getIdPrestamo());
        assertNotNull(resultado.getFechaEntrega());
    }

    @Test
    void guardarRechazaPrestamoNoActivo() {
        PrestamoDTO prestamo = new PrestamoDTO();
        prestamo.setEstado("DEVUELTO");

        when(prestamoClient.obtenerPrestamo(3L)).thenReturn(prestamo);

        DevolucionDTO request = new DevolucionDTO();
        request.setIdPrestamo(3L);

        assertThrows(ReglaNegocioException.class, () -> service.guardar(request));
    }

    @Test
    void obtenerPorIdDevuelveDevolucionCuandoExiste() {
        Devolucion devolucion = new Devolucion(15L, 7L, LocalDateTime.of(2026, 6, 8, 11, 0));
        when(repository.findById(15L)).thenReturn(Optional.of(devolucion));

        DevolucionDTO resultado = service.obtenerPorId(15L);

        assertEquals(15L, resultado.getIdDevolucion());
        assertEquals(7L, resultado.getIdPrestamo());
    }

    @Test
    void obtenerPorIdLanzaNotFoundCuandoNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void eliminarValidaExistenciaAntesDeBorrar() {
        when(repository.existsById(8L)).thenReturn(true);

        service.eliminar(8L);

        verify(repository).deleteById(8L);
    }
}
