package cl.duoc.ms_sucursales.service.impl;

import cl.duoc.ms_sucursales.dto.SucursalDTO;
import cl.duoc.ms_sucursales.model.Sucursal;
import cl.duoc.ms_sucursales.repository.SucursalRepository;
import cl.duoc.ms_sucursales.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalServiceImpl implements SucursalService {

    @Autowired
    private SucursalRepository repository;

    @Override
    public List<SucursalDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public SucursalDTO obtenerPorId(Long id) {
        Sucursal sucursal = repository.findById(id).orElseThrow(() -> new cl.duoc.ms_sucursales.exception.RecursoNoEncontradoException("Sucursal no encontrada"));
        return toDTO(sucursal);
    }

    @Override
    public SucursalDTO guardar(SucursalDTO dto) {
        Sucursal sucursal = toEntity(dto);
        return toDTO(repository.save(sucursal));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private SucursalDTO toDTO(Sucursal entity) {
        SucursalDTO dto = new SucursalDTO();
        dto.setIdSucursal(entity.getIdSucursal());
        dto.setNombreSede(entity.getNombreSede());
        dto.setDireccion(entity.getDireccion());
        return dto;
    }

    private Sucursal toEntity(SucursalDTO dto) {
        Sucursal entity = new Sucursal();
        entity.setIdSucursal(dto.getIdSucursal());
        entity.setNombreSede(dto.getNombreSede());
        entity.setDireccion(dto.getDireccion());
        return entity;
    }
}
