package cl.duoc.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_inventario.model.InventarioEntity;
import java.util.List;



@Repository
public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    List<InventarioEntity> findByIsbn(String isbn);
}
