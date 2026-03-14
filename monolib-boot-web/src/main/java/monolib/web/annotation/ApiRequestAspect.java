package monolib.web.annotation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.context.ContextHolder;
import monolib.core.exception.BusinessException;
import monolib.core.model.Session;
import monolib.core.validator.AuthenticationCoreValidator;
import monolib.core.validator.PermissionCoreValidator;
import monolib.web.cookie.CookieService;
import monolib.web.permission.WebPermissionCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiRequestAspect {

    AuthenticationCoreValidator authenticationValidator;

    PermissionCoreValidator permissionValidator;

    CookieService cookieService;

    @Around("bean(*Handler)")
    public Object serviceRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var apiRequest = getAnnotation(method, ApiRequest.class);

        if (apiRequest != null) {
            var handlerRequest = getAnnotation(method, Handler.class);
            if (handlerRequest == null) {
                return joinPoint.proceed();
            }
            var session = ContextHolder.get().getSession();
            validateAuthentication(apiRequest, session);
            validateFirstSession(apiRequest, session);
            validateUserPermissions(WebPermissionCache.get(method, apiRequest, handlerRequest), session);
        }
        return joinPoint.proceed();
    }

    private static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        var annotation = AnnotatedElementUtils.findMergedAnnotation(method, annotationType);
        if (annotation != null) {
            return annotation;
        }
        return AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), annotationType);
    }

    private void validateFirstSession(ApiRequest serviceRequest, Session session) {
        if (serviceRequest.authenticate() && serviceRequest.authenticateFirstSession()) {
            authenticationValidator.validateIsNotFirstSession(session);
        }
    }

    private void validateUserPermissions(String[] permissions, Session session) {
        if (Objects.nonNull(session) && Objects.nonNull(permissions) && permissions.length > 0) {
            permissionValidator.validate(session.getUser(), permissions);
        }
    }

    private void validateAuthentication(ApiRequest annotation, Session session) {
        if (annotation.authenticate()) {
            try {
                authenticationValidator.validateSessionInformed(session);
            } catch (BusinessException e){
                cookieService.clearAccess();
                throw e;
            }
        }
    }

}
