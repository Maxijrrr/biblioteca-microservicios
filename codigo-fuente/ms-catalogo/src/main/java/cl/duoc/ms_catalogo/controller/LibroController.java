package cl.duoc.ms_catalogo.controller;

import cl.duoc.ms_catalogo.dto.LibroDTO;
import cl.duoc.ms_catalogo.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @Operation(summary = "Listar todos los libros del catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libros listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al listar libros")
    })
    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Obtener un libro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Obtener un libro por ISBN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado por ISBN"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado por ISBN")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LibroDTO> obtenerPorIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(service.obtenerPorIsbn(isbn));
    }

    @Operation(summary = "Crear un libro en el catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de libro invalidos")
    })
    @PostMapping
    public ResponseEntity<LibroDTO> crear(@Valid @RequestBody LibroDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroDTO dto) {
        service.obtenerPorId(id);
        dto.setIdLibro(id);
        return ResponseEntity.ok(service.guardar(dto));
    }

    @Operation(summary = "Eliminar un libro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
