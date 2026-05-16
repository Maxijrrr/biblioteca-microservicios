package cl.duoc.ms_penalizaciones.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ms_penalizaciones.persistence.entity.PenalizacionEntity;

public interface PenalizacionRepository extends JpaRepository <PenalizacionEntity, Long> {
    
}
