package cl.duoc.ms_devoluciones.client;

import cl.duoc.ms_devoluciones.dto.PrestamoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "prestamos-client",
    url = "${prestamos.service.url}"
)
public interface PrestamoClient {
    @GetMapping("/api/prestamos/{id}")
    PrestamoDTO obtenerPrestamo(@PathVariable("id") Long id);
}
