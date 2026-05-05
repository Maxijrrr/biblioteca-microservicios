package cl.duoc.ms_perfiles.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_perfiles.dto.CreatePerfilDTO;
import cl.duoc.ms_perfiles.dto.PerfilResponseDTO;
import cl.duoc.ms_perfiles.service.PerfilService;
import cl.duoc.ms_perfiles.util.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {
    private final PerfilService perfilService;

    @PostMapping
    public ResponseEntity <ApiResponse<PerfilResponseDTO>> CrearPerfil (
        @RequestBody @Valid CreatePerfilDTO dataDto ) {
            PerfilResponseDTO perfil = perfilService.crearPerfil(dataDto);
        ApiResponse<PerfilResponseDTO> response = new ApiResponse<>(
                true,
                "Perfil creado exitosamente",
                perfil,
                HttpStatus.CREATED.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{rut}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> buscarPorRut(@PathVariable String rut) {
        ApiResponse<PerfilResponseDTO> response = new ApiResponse<>(
                true,
                "Perfil obtenido exitosamente",
                 perfilService.BuscarPerfilPorRut(rut),
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody @Valid CreatePerfilDTO dto) {

        ApiResponse<PerfilResponseDTO> response = new ApiResponse<>(
                true, "Perfil actualizado exitosamente",
                perfilService.ActualizarPerfil(id, dto), HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{rut}")
    public ResponseEntity<ApiResponse<Void>> eliminarPerfil(@PathVariable String rut) {
        perfilService.eliminarPerfilPorRut(rut);
        ApiResponse<Void> response = new ApiResponse<>(
                true, "Perfil eliminado exitosamente",
                null, HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
    
     

}
