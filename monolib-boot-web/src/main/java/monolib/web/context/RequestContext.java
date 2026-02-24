package monolib.web.context;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.context.Context;
import monolib.core.model.Session;
import monolib.core.service.AuthenticationCoreService;
import monolib.core.translation.LocaleRegistry;
import monolib.web.utils.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestContext extends Context {

    AuthenticationCoreService authenticationService;

    @Override
    protected Session provideSession() {
        var accessToken = getCookie(AttributeConstants.ACCESS_COOKIE);
        if (StringUtil.isNullOrEmpty(accessToken)) {
            return null;
        }
        try {
            return authenticationService.authenticate(accessToken);
        } catch (Exception e) {
            log.error("Error authenticating: ", e);
        }
        return null;
    }

    @Override
    protected Locale provideLocale() {
        return getRequestOptional()
                .map(req -> req.getHeader(AttributeConstants.LANGUAGE))
                .map(LocaleRegistry::getLocale)
                .orElse(null);
    }

    public String getRequestPath() {
        return getRequestOptional()
                .map(HttpServletRequest::getRequestURI)
                .orElse(null);
    }

    public HttpServletResponse getResponse() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getResponse)
                .orElse(null);
    }

    public String getCookie(String cookie) {
        return getRequestOptional()
                .map(HttpServletRequest::getCookies)
                .map(Stream::of)
                .map(cookies -> filterCookie(cookie, cookies))
                .flatMap(Stream::findFirst)
                .map(Cookie::getValue)
                .orElse(null);
    }

    private static Optional<HttpServletRequest> getRequestOptional() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest);
    }

    private static Stream<Cookie> filterCookie(String cookie, Stream<Cookie> cookies) {
        return cookies.filter(rCookie -> cookie.equals(rCookie.getName()));
    }

}
