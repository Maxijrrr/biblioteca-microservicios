package cl.duoc.ms_inventario.service;

import cl.duoc.ms_inventario.dto.InventarioDTO;
import cl.duoc.ms_inventario.dto.InventarioResponseDTO;
import cl.duoc.ms_inventario.exception.NotFoundException;
import cl.duoc.ms_inventario.persistence.entity.InventarioEntity;
import cl.duoc.ms_inventario.persistence.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    public InventarioResponseDTO createInventario(InventarioDTO dto) {
        InventarioEntity entity = InventarioEntity.builder()
                .isbn(dto.isbn())
                .idSucursal(dto.idSucursal())
                .stockTotal(dto.stockTotal())
                .stockDisponible(dto.stockDisponible())
                .build();
        InventarioEntity savedEntity = inventarioRepository.save(entity);
        return new InventarioResponseDTO(
                savedEntity.getIdInventario(),
                savedEntity.getIsbn(),
                savedEntity.getIdSucursal(),
                savedEntity.getStockTotal(),
                savedEntity.getStockDisponible()
        );
        
    }

      public InventarioResponseDTO actualizarInventario(Long idInventario,InventarioDTO dto) {
        InventarioEntity inventarioABuscar= inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado con ID: " + idInventario));

        
        inventarioABuscar.setIsbn(dto.isbn());        inventarioABuscar.setIdSucursal(dto.idSucursal());
        inventarioABuscar.setStockTotal(dto.stockTotal());
        inventarioABuscar.setStockDisponible(dto.stockDisponible());
        InventarioEntity savedEntity = inventarioRepository.save(inventarioABuscar);
        return new InventarioResponseDTO(
                savedEntity.getIdInventario(),
                savedEntity.getIsbn(),
                savedEntity.getIdSucursal(),
                savedEntity.getStockTotal(),
                savedEntity.getStockDisponible()
        );
        
    }
   
    public void eliminarInventario(Long idInventario) {
        InventarioEntity entity = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado con ID: " + idInventario));
        inventarioRepository.delete(entity);
    }

    public InventarioResponseDTO buscarInventario(Long idInventario) {
        InventarioEntity entity = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado con ID: " + idInventario));
        return new InventarioResponseDTO(
                entity.getIdInventario(),
                entity.getIsbn(),
                entity.getIdSucursal(),
                entity.getStockTotal(),
                entity.getStockDisponible()
        );
    }
 

    
 
}
