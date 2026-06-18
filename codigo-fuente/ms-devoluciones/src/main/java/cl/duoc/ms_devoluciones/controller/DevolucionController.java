package cl.duoc.ms_devoluciones.controller;

import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.service.DevolucionService;
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
@RequestMapping("/api/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService service;

    @Operation(summary = "Listar todas las devoluciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devoluciones listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al listar devoluciones")
    })
    @GetMapping
    public ResponseEntity<List<DevolucionDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Obtener una devolucion por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolucion encontrada"),
            @ApiResponse(responseCode = "404", description = "Devolucion no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DevolucionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Registrar una devolucion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Devolucion registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de devolucion invalidos")
    })
    @PostMapping
    public ResponseEntity<DevolucionDTO> crear(@Valid @RequestBody DevolucionDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una devolucion existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolucion actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Devolucion no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DevolucionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody DevolucionDTO dto) {
        service.obtenerPorId(id);
        dto.setIdDevolucion(id);
        return ResponseEntity.ok(service.guardar(dto));
    }

    @Operation(summary = "Eliminar una devolucion por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Devolucion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Devolucion no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
