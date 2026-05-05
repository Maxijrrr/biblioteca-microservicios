package cl.duoc.ms_devoluciones.service.impl;

import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.model.Devolucion;
import cl.duoc.ms_devoluciones.repository.DevolucionRepository;
import cl.duoc.ms_devoluciones.service.DevolucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevolucionServiceImpl implements DevolucionService {

    @Autowired
    private DevolucionRepository repository;

    @Override
    public List<DevolucionDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DevolucionDTO obtenerPorId(Long id) {
        Devolucion devolucion = repository.findById(id)
                .orElseThrow(() -> new cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException("Devolución con ID " + id + " no encontrada"));
        return toDTO(devolucion);
    }

    @Override
    public DevolucionDTO guardar(DevolucionDTO dto) {
        if (dto.getFechaEntrega() == null) {
            dto.setFechaEntrega(LocalDateTime.now());
        }
        Devolucion devolucion = toEntity(dto);
        return toDTO(repository.save(devolucion));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException("No se puede eliminar: Devolución con ID " + id + " no existe");
        }
        repository.deleteById(id);
    }

    private DevolucionDTO toDTO(Devolucion entity) {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdDevolucion(entity.getIdDevolucion());
        dto.setIdPrestamo(entity.getIdPrestamo());
        dto.setFechaEntrega(entity.getFechaEntrega());
        return dto;
    }

    private Devolucion toEntity(DevolucionDTO dto) {
        Devolucion entity = new Devolucion();
        entity.setIdDevolucion(dto.getIdDevolucion());
        entity.setIdPrestamo(dto.getIdPrestamo());
        entity.setFechaEntrega(dto.getFechaEntrega());
        return entity;
    }
}
