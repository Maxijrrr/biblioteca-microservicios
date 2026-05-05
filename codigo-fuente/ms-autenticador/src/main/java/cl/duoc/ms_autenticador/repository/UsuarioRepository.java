package cl.duoc.ms_autenticador.repository;

import cl.duoc.ms_autenticador.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // usuarios por nombre al iniciar sesión
    Optional<Usuario> findByUsername(String username);
}
