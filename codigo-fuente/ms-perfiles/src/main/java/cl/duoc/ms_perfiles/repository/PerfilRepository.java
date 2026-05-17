package cl.duoc.ms_perfiles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ms_perfiles.model.PerfilEntity;

@Repository
public interface PerfilRepository extends JpaRepository <PerfilEntity ,Long> {

    Optional <PerfilEntity> findByRut(String rut) ;


    
    
}
    

