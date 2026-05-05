package cl.duoc.ms_ebooks.repository;

import cl.duoc.ms_ebooks.model.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EbookRepository extends JpaRepository<Ebook, Long> {
    // Buscar solo los que no han sido "borrados"
    List<Ebook> findByActivoTrue();
    
    // Buscar por autor ignorando mayúsculas
    List<Ebook> findByAutorContainingIgnoreCaseAndActivoTrue(String autor);
    
    // Buscar por categoría
    List<Ebook> findByCategoriaAndActivoTrue(String categoria);
}