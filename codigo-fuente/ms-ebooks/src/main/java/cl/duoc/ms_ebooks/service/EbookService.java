package cl.duoc.ms_ebooks.service;

import cl.duoc.ms_ebooks.model.Ebook;
import cl.duoc.ms_ebooks.dto.EbookResponseDTO;
import cl.duoc.ms_ebooks.repository.EbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EbookService {
    @Autowired
    private EbookRepository repository;

    public List<EbookResponseDTO> listarActivos() {
        return repository.findByActivoTrue().stream().map(this::toResponseDTO).toList();
    }

    public List<EbookResponseDTO> buscarPorAutor(String autor) {
        return repository.findByAutorContainingIgnoreCaseAndActivoTrue(autor).stream().map(this::toResponseDTO).toList();
    }

    public EbookResponseDTO guardar(Ebook ebook) {
        return toResponseDTO(repository.save(ebook));
    }

    public Ebook guardarEntidad(Ebook ebook) {
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

    private EbookResponseDTO toResponseDTO(Ebook ebook) {
        EbookResponseDTO dto = new EbookResponseDTO();
        dto.setId(ebook.getId());
        dto.setTitulo(ebook.getTitulo());
        dto.setAutor(ebook.getAutor());
        dto.setIsbn(ebook.getIsbn());
        dto.setCategoria(ebook.getCategoria());
        dto.setDisponible(ebook.isDisponible());
        dto.setActivo(ebook.isActivo());
        return dto;
    }
}
