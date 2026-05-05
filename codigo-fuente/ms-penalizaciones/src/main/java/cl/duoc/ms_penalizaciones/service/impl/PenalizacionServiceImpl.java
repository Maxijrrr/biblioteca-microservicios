package cl.duoc.ms_penalizaciones.service.impl;

import cl.duoc.ms_penalizaciones.dto.PenalizacionDTO;
import cl.duoc.ms_penalizaciones.model.Penalizacion;
import cl.duoc.ms_penalizaciones.repository.PenalizacionRepository;
import cl.duoc.ms_penalizaciones.service.PenalizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenalizacionServiceImpl implements PenalizacionService {

    @Autowired
    private PenalizacionRepository repository;

    @Override
    public List<PenalizacionDTO> listarTodas() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PenalizacionDTO> listarPorPerfil(Long idPerfil) {
        return repository.findByIdPerfil(idPerfil).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PenalizacionDTO> listarPorEstado(String estado) {
        return repository.findByEstado(estado).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PenalizacionDTO obtenerPorId(Long id) {
        Penalizacion penalizacion = repository.findById(id).orElseThrow(() -> new cl.duoc.ms_penalizaciones.exception.RecursoNoEncontradoException("Penalizacion no encontrada"));
        return mapToDTO(penalizacion);
    }

    @Override
    public PenalizacionDTO guardar(PenalizacionDTO dto) {
        Penalizacion penalizacion = mapToEntity(dto);
        if (penalizacion.getFechaCreacion() == null) {
            penalizacion.setFechaCreacion(LocalDate.now());
        }
        return mapToDTO(repository.save(penalizacion));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private PenalizacionDTO mapToDTO(Penalizacion entity) {
        PenalizacionDTO dto = new PenalizacionDTO();
        dto.setIdPenalizacion(entity.getIdPenalizacion());
        dto.setIdPerfil(entity.getIdPerfil());
        dto.setIdPrestamo(entity.getIdPrestamo());
        dto.setMonto(entity.getMonto());
        dto.setEstado(entity.getEstado());
        dto.setFechaCreacion(entity.getFechaCreacion());
        return dto;
    }

    private Penalizacion mapToEntity(PenalizacionDTO dto) {
        Penalizacion entity = new Penalizacion();
        entity.setIdPenalizacion(dto.getIdPenalizacion());
        entity.setIdPerfil(dto.getIdPerfil());
        entity.setIdPrestamo(dto.getIdPrestamo());
        entity.setMonto(dto.getMonto());
        entity.setEstado(dto.getEstado());
        entity.setFechaCreacion(dto.getFechaCreacion());
        return entity;
    }
}
