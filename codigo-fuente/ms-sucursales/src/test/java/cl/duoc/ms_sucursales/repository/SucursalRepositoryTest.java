package cl.duoc.ms_sucursales.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.ms_sucursales.model.Sucursal;

@DataJpaTest
class SucursalRepositoryTest {

    @Autowired
    private SucursalRepository repository;

    @Test
    @DisplayName("Debe guardar una sucursal en H2")
    void debeGuardarSucursal() {
        Sucursal sucursal = new Sucursal(null, "Sucursal Guardada", "Direccion 1");

        Sucursal guardada = repository.save(sucursal);

        assertTrue(guardada.getIdSucursal() > 0);
        assertEquals("Sucursal Guardada", guardada.getNombreSede());
    }

    @Test
    @DisplayName("Debe buscar sucursal por ID")
    void debeBuscarSucursalPorId() {
        Sucursal guardada = repository.save(new Sucursal(null, "Sucursal ID", "Direccion 2"));

        Optional<Sucursal> encontrada = repository.findById(guardada.getIdSucursal());

        assertTrue(encontrada.isPresent());
        assertEquals("Direccion 2", encontrada.get().getDireccion());
    }

    @Test
    @DisplayName("Debe listar sucursales registradas")
    void debeListarSucursalesRegistradas() {
        repository.save(new Sucursal(null, "Sucursal Uno", "Direccion 1"));
        repository.save(new Sucursal(null, "Sucursal Dos", "Direccion 2"));

        List<Sucursal> sucursales = repository.findAll();

        assertEquals(2, sucursales.size());
    }
}
