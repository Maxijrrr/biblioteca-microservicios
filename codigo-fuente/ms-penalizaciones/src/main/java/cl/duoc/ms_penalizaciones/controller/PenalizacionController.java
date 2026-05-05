package cl.duoc.ms_penalizaciones.controller;

import cl.duoc.ms_penalizaciones.dto.PenalizacionDTO;
import cl.duoc.ms_penalizaciones.service.PenalizacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalizaciones")
public class PenalizacionController {

    @Autowired
    private PenalizacionService service;

    @GetMapping
    public ResponseEntity<List<PenalizacionDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PenalizacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/perfil/{idPerfil}")
    public ResponseEntity<List<PenalizacionDTO>> listarPorPerfil(@PathVariable Long idPerfil) {
        return ResponseEntity.ok(service.listarPorPerfil(idPerfil));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PenalizacionDTO>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<PenalizacionDTO> crear(@Valid @RequestBody PenalizacionDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PenalizacionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PenalizacionDTO dto) {
        service.obtenerPorId(id); // Validar existencia
            dto.setIdPenalizacion(id);
            return ResponseEntity.ok(service.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
            return ResponseEntity.noContent().build();
    }
}
