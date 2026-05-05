package cl.duoc.ms_prestamos.repository;

import cl.duoc.ms_prestamos.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByIdPerfil(Long idPerfil);
    
    
    List<Prestamo> findByEstadoAndFechaDevolucionEsperadaBefore(String estado, LocalDateTime fecha);
    
    List<Prestamo> findByIsbn(String isbn);
}