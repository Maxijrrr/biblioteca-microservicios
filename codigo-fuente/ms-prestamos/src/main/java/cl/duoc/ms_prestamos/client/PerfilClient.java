package cl.duoc.ms_prestamos.client;

import cl.duoc.ms_prestamos.dto.PerfilDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "perfiles-client",
    url = "${perfiles.service.url}"
)
public interface PerfilClient {
    @GetMapping("/api/v1/perfiles/{id}")
    PerfilDTO obtenerPerfil(@PathVariable("id") Long id);
}
