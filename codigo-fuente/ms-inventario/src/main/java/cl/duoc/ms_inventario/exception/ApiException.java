package cl.duoc.ms_inventario.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiException {

    private final String message;
    private final String path;
    private final int status;
    private final LocalDateTime timestamp;
    private final List<String> details;

    public ApiException(String message, String path, int status,
                        LocalDateTime timestamp, List<String> details) {
        this.message   = message;
        this.path      = path;
        this.status    = status;
        this.timestamp = timestamp;
        this.details   = details;
    }

    public String getMessage()         { return message; }
    public String getPath()            { return path; }
    public int getStatus()             { return status; }
    public LocalDateTime getTimestamp(){ return timestamp; }
    public List<String> getDetails()   { return details; }
}
    
    

