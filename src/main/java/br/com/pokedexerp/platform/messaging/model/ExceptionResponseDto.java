package br.com.pokedexerp.platform.messaging.model;

import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ExceptionResponseDto {

    private Instant timestamp;
    private int status;
    private String message;
    private String path;

    public ExceptionResponseDto(ServiceException exception) {
        this(exception.getStatus(), exception.getMessage());
    }

    public ExceptionResponseDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
        this.path = ServiceContext.getRequestPath();
        this.timestamp = Instant.now();
    }

}
