package cl.duoc.ms_autenticador.dto;

import lombok.Data;
import lombok.AllArgsConstructor; 
import lombok.NoArgsConstructor;  

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String rol;
}
