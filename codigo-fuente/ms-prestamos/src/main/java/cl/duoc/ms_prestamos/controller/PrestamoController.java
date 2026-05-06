package cl.duoc.ms_prestamos.controller;

import cl.duoc.ms_prestamos.dto.PrestamoDTO;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;
import cl.duoc.ms_prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService service;

    @PostMapping("/solicitar")
    public ResponseEntity<PrestamoResponseDTO> solicitar(@Valid @RequestBody PrestamoDTO dto) {
        return new ResponseEntity<>(service.crearPrestamo(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PatchMapping("/renovar/{id}")
    public ResponseEntity<PrestamoResponseDTO> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(service.renovarPrestamo(id));
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<PrestamoResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarPrestamo(id));
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoResponseDTO>> listarVencidos() {
        return ResponseEntity.ok(service.obtenerVencidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PrestamoResponseDTO>> listarPorUsuario(@PathVariable Long id) {
        // Cambiamos 'listarPorUsuario' por 'listarPorAlumno' que es como está en tu Service
        return ResponseEntity.ok(service.listarPorAlumno(id));
    }

}
