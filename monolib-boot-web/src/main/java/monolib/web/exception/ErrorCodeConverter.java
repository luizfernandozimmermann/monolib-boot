package monolib.web.exception;

import lombok.experimental.UtilityClass;
import monolib.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@UtilityClass
public class ErrorCodeConverter {
    public static HttpStatus convert(ErrorCode errorCode) {
        return switch (errorCode) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case UNKNOWN -> HttpStatus.INTERNAL_SERVER_ERROR;
            case CONFLICT -> HttpStatus.CONFLICT;
        };
    }
}
