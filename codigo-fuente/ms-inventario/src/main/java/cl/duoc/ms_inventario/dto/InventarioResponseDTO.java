package cl.duoc.ms_inventario.dto;


public record InventarioResponseDTO(
      Long idInventario,
    
    
    String isbn,
    
    
    Long idSucursal,
    

   
    Integer stockTotal,
    
    
    
     Integer stockDisponible
) {
    
}
