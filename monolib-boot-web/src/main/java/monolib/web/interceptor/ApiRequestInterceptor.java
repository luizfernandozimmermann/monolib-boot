package monolib.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.context.ContextHolder;
import monolib.core.exception.BusinessException;
import monolib.core.model.Session;
import monolib.core.validator.AuthenticationCoreValidator;
import monolib.core.validator.PermissionCoreValidator;
import monolib.web.api.ApiRequest;
import monolib.web.api.Handler;
import monolib.web.context.RequestContext;
import monolib.web.cookie.CookieService;
import monolib.web.permission.WebPermissionCache;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiRequestInterceptor implements HandlerInterceptor {

    RequestContext requestContext;
    
    AuthenticationCoreValidator authenticationValidator;

    PermissionCoreValidator permissionValidator;

    CookieService cookieService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        registerContext();
        validateAuth(handler);
        return true;
    }

    private void registerContext() {
        requestContext.register();
        var context = (RequestContext) ContextHolder.get();
        log.info("{} - Handling message - {}", context.getEmail(), context.getRequestPath());
    }

    private void validateAuth(@NonNull Object handler) {
        if (!(handler instanceof HandlerMethod hm)) {
            return;
        }
        var method = hm.getMethod();
        var clazz = hm.getBeanType();
        var handlerAnnotation = AnnotatedElementUtils.findMergedAnnotation(clazz, Handler.class);
        var requestAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ApiRequest.class);
        if (handlerAnnotation == null || requestAnnotation == null) {
            return;
        }
        var session = ContextHolder.get().getSession();
        validateAuthentication(requestAnnotation, session);
        validateFirstSession(requestAnnotation, session);
        validateUserPermissions(WebPermissionCache.get(method, requestAnnotation, handlerAnnotation), session);
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
