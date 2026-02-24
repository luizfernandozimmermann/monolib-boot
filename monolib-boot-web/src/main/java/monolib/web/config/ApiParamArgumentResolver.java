package monolib.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ApiParamArgumentResolver extends AbstractMessageConverterMethodArgumentResolver {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ApiParamArgumentResolver(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var method = parameter.getMethod();
        if (Objects.isNull(method)) {
            return false;
        }

        return method.getParameterTypes().length > 0;
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        var resolvedUUID = resolveUUID(parameter, webRequest);
        if (resolvedUUID != null) {
            return resolvedUUID;
        }
        var object = readWithMessageConverters(
                webRequest,
                parameter,
                parameter.getNestedGenericParameterType()
        );
        validateObject(object);
        return object;
    }

    private static void validateObject(Object object) {
        validator.validate(object).stream().findFirst().ifPresent(violation -> {
            var errorMessage = "%s %s".formatted(violation.getPropertyPath(), violation.getMessage());
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, errorMessage);
        });
    }

    @SuppressWarnings("unchecked")
    private static @Nullable UUID resolveUUID(@NonNull MethodParameter parameter, @NonNull NativeWebRequest webRequest) {
        var request = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class));
        var type = parameter.getParameterType();
        if (type == UUID.class) {
            var pathVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return UUID.fromString(pathVars.values().iterator().next());
        }
        return null;
    }

}
