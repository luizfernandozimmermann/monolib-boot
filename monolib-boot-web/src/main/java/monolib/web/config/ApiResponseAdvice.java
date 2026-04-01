package monolib.web.config;

import monolib.web.api.ApiRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.Objects;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        var method = returnType.getMethod();
        if (method == null) {
            return false;
        }
        return Arrays.stream(method.getAnnotations())
                .anyMatch(a -> a.annotationType().isAnnotationPresent(ApiRequest.class));
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {
        var apiRequest = Arrays.stream(Objects.requireNonNull(returnType.getMethod()).getAnnotations())
                .map(a -> a.annotationType().getAnnotation(ApiRequest.class))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();

        if (apiRequest.method() == RequestMethod.DELETE) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return null;
        }

        return body;
    }

}
