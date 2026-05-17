package cl.duoc.ms_inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.ms_inventario.dto.SucursalDTO;

@FeignClient(name = "ms-sucursales", url = "${sucursales.service.url}")

public interface SucursalClient {

    @GetMapping("/api/sucursales/{id}")
    SucursalDTO getSucursal(@PathVariable("id") Long id);

}
