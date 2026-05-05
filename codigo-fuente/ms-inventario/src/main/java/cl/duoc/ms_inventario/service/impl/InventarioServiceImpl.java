package cl.duoc.ms_inventario.service.impl;

import cl.duoc.ms_inventario.dto.InventarioDTO;
import cl.duoc.ms_inventario.model.Inventario;
import cl.duoc.ms_inventario.repository.InventarioRepository;
import cl.duoc.ms_inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository repository;

    @Override
    public List<InventarioDTO> listarTodos() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InventarioDTO> listarPorIsbn(String isbn) {
        return repository.findByIsbn(isbn).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InventarioDTO> listarPorSucursal(Long idSucursal) {
        return repository.findByIdSucursal(idSucursal).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public InventarioDTO obtenerPorId(Long id) {
        Inventario inventario = repository.findById(id).orElseThrow(() -> new cl.duoc.ms_inventario.exception.RecursoNoEncontradoException("Inventario no encontrado"));
        return mapToDTO(inventario);
    }

    @Override
    public InventarioDTO guardar(InventarioDTO dto) {
        Inventario inventario = mapToEntity(dto);
        return mapToDTO(repository.save(inventario));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private InventarioDTO mapToDTO(Inventario entity) {
        InventarioDTO dto = new InventarioDTO();
        dto.setIdInventario(entity.getIdInventario());
        dto.setIsbn(entity.getIsbn());
        dto.setIdSucursal(entity.getIdSucursal());
        dto.setStockTotal(entity.getStockTotal());
        dto.setStockDisponible(entity.getStockDisponible());
        return dto;
    }

    private Inventario mapToEntity(InventarioDTO dto) {
        Inventario entity = new Inventario();
        entity.setIdInventario(dto.getIdInventario());
        entity.setIsbn(dto.getIsbn());
        entity.setIdSucursal(dto.getIdSucursal());
        entity.setStockTotal(dto.getStockTotal());
        entity.setStockDisponible(dto.getStockDisponible());
        return entity;
    }
}
