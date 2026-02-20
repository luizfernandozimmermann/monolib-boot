package br.com.pokedexerp.platform.messaging.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public ServiceException(HttpStatus status) {
        super();
        this.status = status;
    }

    public ServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ServiceException(HttpStatus status, Exception e) {
        super(e);
        this.status = status;
    }

    public ServiceException(HttpStatus status, String message, Exception e) {
        super(message, e);
        this.status = status;
    }

}
