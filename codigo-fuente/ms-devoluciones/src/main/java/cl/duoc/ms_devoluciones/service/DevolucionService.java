package cl.duoc.ms_devoluciones.service;

import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import java.util.List;

public interface DevolucionService {
    List<DevolucionDTO> listarTodas();
    DevolucionDTO obtenerPorId(Long id);
    DevolucionDTO guardar(DevolucionDTO dto);
    void eliminar(Long id);
}
