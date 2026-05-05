package cl.duoc.ms_sucursales.service;

import cl.duoc.ms_sucursales.dto.SucursalDTO;
import java.util.List;

public interface SucursalService {
    List<SucursalDTO> listarTodas();
    SucursalDTO obtenerPorId(Long id);
    SucursalDTO guardar(SucursalDTO sucursalDTO);
    void eliminar(Long id);
}
