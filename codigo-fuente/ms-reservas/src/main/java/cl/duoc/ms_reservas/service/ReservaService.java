package cl.duoc.ms_reservas.service;

import cl.duoc.ms_reservas.dto.ReservaDTO;
import java.util.List;

public interface ReservaService {
    List<ReservaDTO> listarTodas();
    ReservaDTO obtenerPorId(Long id);
    ReservaDTO guardar(ReservaDTO dto);
    void eliminar(Long id);
}
