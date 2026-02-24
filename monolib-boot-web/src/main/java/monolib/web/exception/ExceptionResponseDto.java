package monolib.web.exception;

import lombok.Data;
import monolib.core.context.ContextHolder;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.web.context.RequestContext;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ExceptionResponseDto {

    private Instant timestamp;
    private int status;
    private String message;
    private String path;

    public ExceptionResponseDto(BusinessException exception) {
        this(exception.getErrorCode(), exception.getMessage());
    }

    public ExceptionResponseDto(ErrorCode errorCode, String message) {
        this(ErrorCodeConverter.convert(errorCode), message);
    }

    public ExceptionResponseDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
        this.timestamp = Instant.now();
        this.path = ((RequestContext) ContextHolder.get()).getRequestPath();
    }

}
