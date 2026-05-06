package cl.duoc.ms_devoluciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_devoluciones.client.PrestamoClient;
import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.dto.PrestamoDTO;
import cl.duoc.ms_devoluciones.exception.ReglaNegocioException;
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
}
