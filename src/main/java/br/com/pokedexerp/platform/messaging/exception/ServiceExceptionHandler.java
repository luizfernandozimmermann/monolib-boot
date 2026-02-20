package br.com.pokedexerp.platform.messaging.exception;

import br.com.pokedexerp.platform.messaging.model.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponseDto> handle(ServiceException e) {
        return ResponseEntity.status(e.getStatus()).body(new ExceptionResponseDto(e));
    }

}
