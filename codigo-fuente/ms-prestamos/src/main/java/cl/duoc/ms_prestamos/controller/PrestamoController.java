package cl.duoc.ms_prestamos.controller;

import cl.duoc.ms_prestamos.dto.PrestamoDTO;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;
import cl.duoc.ms_prestamos.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService service;

    @Operation(summary = "Solicitar un prestamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prestamo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de prestamo invalidos"),
            @ApiResponse(responseCode = "503", description = "Servicio dependiente no disponible")
    })
    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> solicitar(@Valid @RequestBody PrestamoDTO dto) {
        return new ResponseEntity<>(service.crearPrestamo(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los prestamos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamos listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al listar prestamos")
    })
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Renovar un prestamo activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo renovado correctamente"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    @PatchMapping("/renovar/{id}")
    public ResponseEntity<PrestamoResponseDTO> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(service.renovarPrestamo(id));
    }

    @Operation(summary = "Finalizar un prestamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo finalizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    @PutMapping("/finalizar/{id}")
    public ResponseEntity<PrestamoResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizarPrestamo(id));
    }

    @Operation(summary = "Listar prestamos vencidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamos vencidos listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al listar prestamos vencidos")
    })
    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoResponseDTO>> listarVencidos() {
        return ResponseEntity.ok(service.obtenerVencidos());
    }

    @Operation(summary = "Obtener un prestamo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Listar prestamos por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamos del usuario listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario sin prestamos asociados")
    })
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PrestamoResponseDTO>> listarPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorAlumno(id));
    }
}
