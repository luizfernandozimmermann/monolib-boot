package monolib.web.exception;

import monolib.core.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebBusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseDto> handle(BusinessException e) {
        var httpStatus = ErrorCodeConverter.convert(e.getErrorCode());
        return ResponseEntity.status(httpStatus).body(new ExceptionResponseDto(httpStatus, e.getMessage()));
    }

}
