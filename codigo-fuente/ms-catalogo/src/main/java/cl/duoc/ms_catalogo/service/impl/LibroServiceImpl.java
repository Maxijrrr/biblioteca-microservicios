package cl.duoc.ms_catalogo.service.impl;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import cl.duoc.ms_catalogo.model.Libro;
import cl.duoc.ms_catalogo.repository.LibroRepository;
import cl.duoc.ms_catalogo.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository repository;

    @Override
    public List<LibroDTO> listarTodos() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public LibroDTO obtenerPorId(Long id) {
        Libro libro = repository.findById(id)
                .orElseThrow(() -> new cl.duoc.ms_catalogo.exception.RecursoNoEncontradoException("Libro no encontrado con ID: " + id));
        return mapToDTO(libro);
    }

    @Override
    public LibroDTO obtenerPorIsbn(String isbn) {
        Libro libro = repository.findByIsbn(isbn)
                .orElseThrow(() -> new cl.duoc.ms_catalogo.exception.RecursoNoEncontradoException("Libro no encontrado por ISBN: " + isbn));
        return mapToDTO(libro);
    }

    @Override
    public LibroDTO guardar(LibroDTO dto) {
        Libro libro = mapToEntity(dto);
        return mapToDTO(repository.save(libro));
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id); // Validar existencia antes de borrar
        repository.deleteById(id);
    }

    private LibroDTO mapToDTO(Libro entity) {
        LibroDTO dto = new LibroDTO();
        dto.setIdLibro(entity.getIdLibro());
        dto.setIsbn(entity.getIsbn());
        dto.setTitulo(entity.getTitulo());
        dto.setAutor(entity.getAutor());
        dto.setEditorial(entity.getEditorial());
        dto.setAnioPublicacion(entity.getAnioPublicacion());
        dto.setCategoria(entity.getCategoria());
        return dto;
    }

    private Libro mapToEntity(LibroDTO dto) {
        Libro entity = new Libro();
        entity.setIdLibro(dto.getIdLibro());
        entity.setIsbn(dto.getIsbn());
        entity.setTitulo(dto.getTitulo());
        entity.setAutor(dto.getAutor());
        entity.setEditorial(dto.getEditorial());
        entity.setAnioPublicacion(dto.getAnioPublicacion());
        entity.setCategoria(dto.getCategoria());
        return entity;
    }
}
