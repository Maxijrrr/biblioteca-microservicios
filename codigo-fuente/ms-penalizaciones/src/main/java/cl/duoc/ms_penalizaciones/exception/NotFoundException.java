package cl.duoc.ms_penalizaciones.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseApiException {
    public NotFoundException (String mensaje){
        super(mensaje,HttpStatus.NOT_FOUND.value());

    }

}
