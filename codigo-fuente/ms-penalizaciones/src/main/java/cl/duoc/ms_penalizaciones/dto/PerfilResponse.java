package cl.duoc.ms_penalizaciones.dto;

public record PerfilResponse(
    boolean success,
    String message,
    PerfilDTO data,
    int status
) {
    
}
