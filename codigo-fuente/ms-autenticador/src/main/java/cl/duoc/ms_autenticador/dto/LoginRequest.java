package cl.duoc.ms_autenticador.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}