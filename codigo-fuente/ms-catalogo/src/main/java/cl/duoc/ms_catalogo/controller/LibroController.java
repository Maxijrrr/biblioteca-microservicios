package cl.duoc.ms_catalogo.controller;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import cl.duoc.ms_catalogo.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LibroDTO> obtenerPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(service.obtenerPorIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<LibroDTO> crear(@Valid @RequestBody LibroDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroDTO dto) {
        service.obtenerPorId(id); // Validar existencia
        dto.setIdLibro(id);
        return ResponseEntity.ok(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
