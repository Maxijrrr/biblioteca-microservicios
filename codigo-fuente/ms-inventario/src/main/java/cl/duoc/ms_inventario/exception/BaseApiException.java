package cl.duoc.ms_inventario.exception;

public abstract class  BaseApiException extends RuntimeException  {
    private final int statusCode;

    protected BaseApiException(String mensaje, int statusCode) {
        super(mensaje);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
