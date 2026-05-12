package cl.duoc.ms_inventario.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_inventario.persistence.entity.InventarioEntity;



@Repository
public interface InventarioRepository extends JpaRepository<InventarioEntity, Long> {
    
}