package cl.duoc.ms_penalizaciones.service;

import cl.duoc.ms_penalizaciones.dto.PenalizacionDTO;
import java.util.List;

public interface PenalizacionService {
    List<PenalizacionDTO> listarTodas();
    List<PenalizacionDTO> listarPorPerfil(Long idPerfil);
    List<PenalizacionDTO> listarPorEstado(String estado);
    PenalizacionDTO obtenerPorId(Long id);
    PenalizacionDTO guardar(PenalizacionDTO dto);
    void eliminar(Long id);
}
