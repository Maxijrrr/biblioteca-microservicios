package cl.duoc.ms_penalizaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ms_penalizaciones.model.PenalizacionEntity;
public interface PenalizacionRepository extends JpaRepository <PenalizacionEntity, Long> {
    
}
