package cl.duoc.ms_penalizaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cl.duoc.ms_penalizaciones.dto.PrestamoDTO;

@FeignClient(
    name = "prestamos-client",
    url = "${prestamos.service.url}"
)
public interface PrestamoClient {
    @GetMapping("/api/v1/prestamos/{id}")
    PrestamoDTO obtenerPrestamo(@PathVariable("id") Long id);
    
}