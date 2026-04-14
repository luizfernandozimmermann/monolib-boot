package monolib.web.exception;

import monolib.core.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebBusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseDto> handle(BusinessException e) {
        var httpStatus = ErrorCodeConverter.convert(e.getErrorCode());
        return ResponseEntity.status(httpStatus).body(new ExceptionResponseDto(httpStatus, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handle(MethodArgumentNotValidException e) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse("Validation error");
        return ResponseEntity.status(httpStatus).body(new ExceptionResponseDto(httpStatus, message));
    }

}
