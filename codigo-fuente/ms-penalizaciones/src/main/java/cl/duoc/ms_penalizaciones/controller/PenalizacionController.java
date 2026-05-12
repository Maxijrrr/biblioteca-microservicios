package cl.duoc.ms_penalizaciones.controller;

import cl.duoc.ms_penalizaciones.dto.CreatePenalizacionDTO;
import cl.duoc.ms_penalizaciones.dto.PenalizacionResponseDTO;
import cl.duoc.ms_penalizaciones.service.PenalizacionService;
import cl.duoc.ms_penalizaciones.util.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/penalizaciones")
@RequiredArgsConstructor
public class PenalizacionController {

    private final PenalizacionService penalizacionService;

   @PostMapping
    public ResponseEntity<ApiResponse<Void>> agregarPenalizacion(
            @Valid @RequestBody CreatePenalizacionDTO dto) {

        penalizacionService.createPenalizacion(dto);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Penalizacion creado con exito",
                null,
                HttpStatus.CREATED.value()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PenalizacionResponseDTO>> buscarPenalizacion(
            @PathVariable Long id) {

        PenalizacionResponseDTO data = penalizacionService.buscarPenalizacion(id);
        ApiResponse<PenalizacionResponseDTO> response = new ApiResponse<>(
                true,
                "Penalización encontrada",
                data,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PenalizacionResponseDTO>> modificarPenalizacion(
            @PathVariable Long id,
            @Valid @RequestBody CreatePenalizacionDTO dto) {

        PenalizacionResponseDTO data = penalizacionService.actualizarPenalizacion(id, dto);
        ApiResponse<PenalizacionResponseDTO> response = new ApiResponse<>(
                true,
                "Penalización modificada con éxito",
                data,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarPenalizacion(
            @PathVariable Long id) {

        penalizacionService.eliminarPenalizacion(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Penalización eliminada con éxito",
                null,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }}

