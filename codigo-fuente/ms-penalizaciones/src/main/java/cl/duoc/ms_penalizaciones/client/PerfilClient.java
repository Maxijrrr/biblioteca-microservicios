package cl.duoc.ms_penalizaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.ms_penalizaciones.dto.PerfilResponse;

@FeignClient(
    name = "perfiles-client",
    url = "${perfiles.service.url}"
)
public interface PerfilClient {
    @GetMapping("/api/v1/perfiles/{id}")
    PerfilResponse buscarPorId(@PathVariable("id") Long id);
    
}