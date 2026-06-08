package cl.duoc.ms_sucursales.service;

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

import cl.duoc.ms_sucursales.dto.SucursalDTO;
import cl.duoc.ms_sucursales.exception.RecursoNoEncontradoException;
import cl.duoc.ms_sucursales.model.Sucursal;
import cl.duoc.ms_sucursales.repository.SucursalRepository;
import cl.duoc.ms_sucursales.service.impl.SucursalServiceImpl;

@ExtendWith(MockitoExtension.class)
class SucursalServiceImplTest {

    @Mock
    private SucursalRepository repository;

    @InjectMocks
    private SucursalServiceImpl service;

    @Test
    @DisplayName("Debe listar sucursales como DTO")
    void debeListarSucursalesComoDto() {
        when(repository.findAll()).thenReturn(List.of(new Sucursal(1L, "Sucursal Lista", "Direccion Lista")));

        List<SucursalDTO> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        assertEquals("Sucursal Lista", resultado.get(0).getNombreSede());
    }

    @Test
    @DisplayName("Debe obtener sucursal por ID cuando existe")
    void debeObtenerSucursalPorIdCuandoExiste() {
        when(repository.findById(2L)).thenReturn(Optional.of(new Sucursal(2L, "Sucursal ID", "Direccion ID")));

        SucursalDTO resultado = service.obtenerPorId(2L);

        assertEquals(2L, resultado.getIdSucursal());
        assertEquals("Direccion ID", resultado.getDireccion());
    }

    @Test
    @DisplayName("Debe lanzar 404 cuando la sucursal no existe")
    void debeLanzarNotFoundCuandoSucursalNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    @DisplayName("Debe guardar sucursal desde DTO")
    void debeGuardarSucursalDesdeDto() {
        SucursalDTO request = crearDto(null, "Nueva Sede", "Direccion Nueva");
        when(repository.save(any(Sucursal.class))).thenReturn(new Sucursal(7L, "Nueva Sede", "Direccion Nueva"));

        SucursalDTO resultado = service.guardar(request);

        assertEquals(7L, resultado.getIdSucursal());
        assertEquals("Nueva Sede", resultado.getNombreSede());
    }

    @Test
    @DisplayName("Debe delegar eliminacion en el repositorio")
    void debeDelegarEliminacionEnRepositorio() {
        service.eliminar(4L);

        verify(repository).deleteById(4L);
    }

    private SucursalDTO crearDto(Long id, String nombre, String direccion) {
        SucursalDTO dto = new SucursalDTO();
        dto.setIdSucursal(id);
        dto.setNombreSede(nombre);
        dto.setDireccion(direccion);
        return dto;
    }
}
