package cl.duoc.ms_inventario.service;

import cl.duoc.ms_inventario.dto.InventarioDTO;
import java.util.List;

public interface InventarioService {
    List<InventarioDTO> listarTodos();
    List<InventarioDTO> listarPorIsbn(String isbn);
    List<InventarioDTO> listarPorSucursal(Long idSucursal);
    InventarioDTO obtenerPorId(Long id);
    InventarioDTO guardar(InventarioDTO dto);
    void eliminar(Long id);
}
