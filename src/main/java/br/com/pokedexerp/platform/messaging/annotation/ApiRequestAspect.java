package br.com.pokedexerp.platform.messaging.annotation;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.authentication.service.CookieService;
import br.com.pokedexerp.platform.authentication.validator.UserRoleValidator;
import br.com.pokedexerp.platform.entity.utils.ReflectionUtils;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.messaging.utils.PermissionCache;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiRequestAspect {

    TranslationService translationService;

    UserRoleValidator userRoleValidator;

    CookieService cookieService;

    @Around("@within(Handler) || @within(EntityHandler)")
    public Object serviceRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var apiRequest = ReflectionUtils.getAnnotation(method, ApiRequest.class);
        var handlerRequest = ReflectionUtils.getAnnotation(method, Handler.class);

        if (apiRequest != null && handlerRequest != null) {
            var session = ServiceContext.getSession();
            validateAuthentication(apiRequest, session);
            validateFirstSession(apiRequest, session);
            validateUserPermissions(PermissionCache.get(method, apiRequest, handlerRequest), session);
        }

        return joinPoint.proceed();
    }

    private void validateFirstSession(ApiRequest serviceRequest, UserSessionDto session) {
        if (serviceRequest.authenticate() && serviceRequest.authenticateFirstSession() && session.isFirstSession()) {
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

    private void validateUserPermissions(String[] permissions, UserSessionDto session) {
        if (Objects.nonNull(session) && Objects.nonNull(permissions) && permissions.length > 0) {
            userRoleValidator.validate(session.getUser().getId(), permissions);
        }
    }

    private void validateAuthentication(ApiRequest annotation, UserSessionDto session) {
        if (annotation.authenticate() && Objects.isNull(session)) {
            cookieService.clearAccess();
            var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
            throw new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
        }
    }

}
