package cl.duoc.ms_inventario.repository;

import cl.duoc.ms_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByIsbn(String isbn);
    List<Inventario> findByIdSucursal(Long idSucursal);
    Optional<Inventario> findByIsbnAndIdSucursal(String isbn, Long idSucursal);
}
