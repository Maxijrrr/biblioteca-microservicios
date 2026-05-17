package cl.duoc.ms_inventario.service;

import cl.duoc.ms_inventario.client.SucursalClient;
import cl.duoc.ms_inventario.dto.InventarioDTO;
import cl.duoc.ms_inventario.dto.InventarioResponseDTO;
import cl.duoc.ms_inventario.dto.SucursalDTO;
import cl.duoc.ms_inventario.exception.RecursoNoEncontradoException;
import cl.duoc.ms_inventario.exception.ServicioNoDisponibleException;
import cl.duoc.ms_inventario.model.InventarioEntity;
import cl.duoc.ms_inventario.repository.InventarioRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final SucursalClient sucursalClient;
    public InventarioResponseDTO createInventario(InventarioDTO dto) {
        validarSucursal(dto.idSucursal());

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
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado con ID: " + idInventario));


        validarSucursal(dto.idSucursal());

        
        inventarioABuscar.setIsbn(dto.isbn());
        inventarioABuscar.setIdSucursal(dto.idSucursal());
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
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado con ID: " + idInventario));
        inventarioRepository.delete(entity);
    }

    public InventarioResponseDTO buscarInventario(Long idInventario) {
        InventarioEntity entity = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado con ID: " + idInventario));
        return new InventarioResponseDTO(
                entity.getIdInventario(),
                entity.getIsbn(),
                entity.getIdSucursal(),
                entity.getStockTotal(),
                entity.getStockDisponible()
        );
    }

    public List<InventarioResponseDTO> buscarPorIsbn(String isbn) {
        return inventarioRepository.findByIsbn(isbn)
                .stream()
                .map(entity -> new InventarioResponseDTO(
                        entity.getIdInventario(),
                        entity.getIsbn(),
                        entity.getIdSucursal(),
                        entity.getStockTotal(),
                        entity.getStockDisponible()
                ))
                .toList();
    }

    private void validarSucursal(Long idSucursal) {
    try {
        SucursalDTO sucursal = sucursalClient.getSucursal(idSucursal);

        if (sucursal == null) {
            throw new RecursoNoEncontradoException("Sucursal no encontrada con ID: " + idSucursal);
        }

    } catch (FeignException.NotFound e) {
        throw new RecursoNoEncontradoException("Sucursal no encontrada con ID: " + idSucursal);

    } catch (FeignException e) {
        throw new ServicioNoDisponibleException("No se pudo conectar con el servicio de sucursales.");
    }
}

    
 
}
