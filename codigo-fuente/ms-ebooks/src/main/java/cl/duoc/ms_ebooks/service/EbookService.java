package cl.duoc.ms_ebooks.service;

import cl.duoc.ms_ebooks.model.Ebook;
import cl.duoc.ms_ebooks.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EbookService {
    @Autowired
    private EbookRepository repository;

    public List<Ebook> listarActivos() {
        return repository.findByActivoTrue();
    }

    public List<Ebook> buscarPorAutor(String autor) {
        return repository.findByAutorContainingIgnoreCaseAndActivoTrue(autor);
    }

    public Ebook guardar(Ebook ebook) {
        return repository.save(ebook);
    }

    public Optional<Ebook> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminarLogico(Long id) {
        repository.findById(id).ifPresent(ebook -> {
            ebook.setActivo(false);
            repository.save(ebook);
        });
    }
}