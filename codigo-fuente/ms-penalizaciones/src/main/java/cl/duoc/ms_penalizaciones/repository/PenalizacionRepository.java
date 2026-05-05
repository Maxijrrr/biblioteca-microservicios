package cl.duoc.ms_penalizaciones.repository;

import cl.duoc.ms_penalizaciones.model.Penalizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenalizacionRepository extends JpaRepository<Penalizacion, Long> {
    List<Penalizacion> findByIdPerfil(Long idPerfil);
    List<Penalizacion> findByEstado(String estado);
}
