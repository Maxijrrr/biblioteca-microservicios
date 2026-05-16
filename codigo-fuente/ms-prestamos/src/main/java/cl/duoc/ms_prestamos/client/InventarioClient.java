package cl.duoc.ms_prestamos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(
    name = "inventario-client",
    url = "${inventario.service.url}"
)
public interface InventarioClient {
    @GetMapping("/api/v1/inventario/isbn/{isbn}")
    List<Map<String, Object>> buscarStockPorIsbn(@PathVariable("isbn") String isbn);
}
