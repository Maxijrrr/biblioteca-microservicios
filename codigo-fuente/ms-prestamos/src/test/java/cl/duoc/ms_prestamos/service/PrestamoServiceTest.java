package cl.duoc.ms_prestamos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
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
import cl.duoc.ms_prestamos.client.InventarioClient;
import cl.duoc.ms_prestamos.client.PerfilClient;
import cl.duoc.ms_prestamos.dto.PerfilDTO;
import cl.duoc.ms_prestamos.dto.PrestamoDTO;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository repository;

    @Mock
    private PerfilClient perfilClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private PrestamoService service;

    @Test
    void crearPrestamoValidaPerfilInventarioYGuardaActivo() {
        PerfilDTO perfil = new PerfilDTO();
        perfil.setId(10L);
        perfil.setNombre("Alumno Demo");

        PrestamoDTO request = new PrestamoDTO();
        request.setIdPerfil(10L);
        request.setIsbn("978-stock");

        when(perfilClient.obtenerPerfil(10L)).thenReturn(perfil);
        when(inventarioClient.buscarStockPorIsbn("978-stock"))
                .thenReturn(List.of(Map.of("stockDisponible", 3)));
        when(repository.save(any(Prestamo.class))).thenAnswer(invocation -> {
            Prestamo prestamo = invocation.getArgument(0, Prestamo.class);
            prestamo.setId(100L);
            return prestamo;
        });

        PrestamoResponseDTO resultado = service.crearPrestamo(request);

        assertEquals(100L, resultado.getIdPrestamo());
        assertEquals(10L, resultado.getIdPerfil());
        assertEquals("978-stock", resultado.getIsbn());
        assertEquals("ACTIVO", resultado.getEstado());
        assertNotNull(resultado.getFechaPrestamo());
        assertNotNull(resultado.getFechaDevolucionEsperada());
    }

    @Test
    void crearPrestamoRechazaInventarioSinStock() {
        PerfilDTO perfil = new PerfilDTO();
        perfil.setId(10L);
        perfil.setNombre("Alumno Demo");

        PrestamoDTO request = new PrestamoDTO();
        request.setIdPerfil(10L);
        request.setIsbn("978-sin-stock");

        when(perfilClient.obtenerPerfil(10L)).thenReturn(perfil);
        when(inventarioClient.buscarStockPorIsbn("978-sin-stock"))
                .thenReturn(List.of(Map.of("stockDisponible", 0)));

        assertThrows(RuntimeException.class, () -> service.crearPrestamo(request));
    }

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
