package cl.duoc.ms_inventario.controller;

import cl.duoc.ms_inventario.dto.InventarioDTO;
import cl.duoc.ms_inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<List<InventarioDTO>> listarPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(service.listarPorIsbn(isbn));
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<InventarioDTO>> listarPorSucursal(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(service.listarPorSucursal(idSucursal));
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@Valid @RequestBody InventarioDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioDTO dto) {
        service.obtenerPorId(id); // Validar existencia
            dto.setIdInventario(id);
            return ResponseEntity.ok(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
            return ResponseEntity.noContent().build();
    }
}
