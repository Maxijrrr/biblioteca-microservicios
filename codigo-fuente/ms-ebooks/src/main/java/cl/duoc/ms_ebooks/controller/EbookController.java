package cl.duoc.ms_ebooks.controller;

import cl.duoc.ms_ebooks.dto.EbookDTO;
import cl.duoc.ms_ebooks.dto.EbookResponseDTO;
import cl.duoc.ms_ebooks.model.Ebook;
import cl.duoc.ms_ebooks.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ebooks")
public class EbookController {

    @Autowired
    private EbookService service;

    @GetMapping
    public List<EbookResponseDTO> listar() {
        return service.listarActivos();
    }

    @GetMapping("/buscar")
    public List<EbookResponseDTO> buscar(@RequestParam String autor) {
        return service.buscarPorAutor(autor);
    }

    @PostMapping
    public EbookResponseDTO crear(@RequestBody EbookDTO dto) {
        Ebook ebook = new Ebook();
        ebook.setTitulo(dto.getTitulo());
        ebook.setAutor(dto.getAutor());
        ebook.setIsbn(dto.getIsbn());
        ebook.setCategoria(dto.getCategoria());
        return service.guardar(ebook);
    }

    @PutMapping("/{id}/prestar")
    public String prestar(@PathVariable Long id) {
        return service.buscarPorId(id).map(ebook -> {
            if (!ebook.isDisponible()) return "El libro ya está prestado.";
            ebook.setDisponible(false);
            service.guardarEntidad(ebook);
            return "Préstamo realizado con éxito.";
        }).orElse("Libro no encontrado.");
    }

    @PutMapping("/{id}/devolver")
    public String devolver(@PathVariable Long id) {
        return service.buscarPorId(id).map(ebook -> {
            ebook.setDisponible(true);
            service.guardarEntidad(ebook);
            return "Libro devuelto correctamente.";
        }).orElse("Libro no encontrado.");
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminarLogico(id);
        return "Libro eliminado del catálogo.";
    }
}
