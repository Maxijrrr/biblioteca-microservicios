package cl.duoc.ms_perfiles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ms_perfiles.dto.PerfilResponseDTO;
import cl.duoc.ms_perfiles.service.PerfilService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/perfiles")
@RequiredArgsConstructor
public class PerfilInternalController {

    private final PerfilService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.buscarPerfilPorId(id));
    }
}
