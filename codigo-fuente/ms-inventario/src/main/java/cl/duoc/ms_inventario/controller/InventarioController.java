package cl.duoc.ms_inventario.controller;

import cl.duoc.ms_inventario.dto.InventarioDTO;
import cl.duoc.ms_inventario.dto.InventarioResponseDTO;
import cl.duoc.ms_inventario.service.InventarioService;
import cl.duoc.ms_inventario.util.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> agregarInventario(
            @Valid @RequestBody InventarioDTO dto) {

        inventarioService.createInventario(dto);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Inventario creado con exito",
                null,
                HttpStatus.CREATED.value()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioResponseDTO>> buscarInventario(
            @PathVariable Long id) {

        InventarioResponseDTO data = inventarioService.buscarInventario(id);
        ApiResponse<InventarioResponseDTO> response = new ApiResponse<>(
                true,
                "Inventario encontrado",
                data,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<List<InventarioResponseDTO>> buscarInventarioPorIsbn(
            @PathVariable String isbn) {

        return ResponseEntity.ok(inventarioService.buscarPorIsbn(isbn));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventarioResponseDTO>> modificarInventario(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO dto) {

        InventarioResponseDTO data = inventarioService.actualizarInventario(id, dto);
        ApiResponse<InventarioResponseDTO> response = new ApiResponse<>(
                true,
                "Inventario modificado con éxito",
                data,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarInventario(
            @PathVariable Long id) {

        inventarioService.eliminarInventario(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Inventario eliminado con éxito",
                null,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }}
