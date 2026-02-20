package br.com.pokedexerp.platform.messaging.context;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.messaging.utils.AttributeConstants;
import br.com.pokedexerp.platform.messaging.utils.LocaleUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

@UtilityClass
public class RequestContext {

    public static UserSessionDto getSession() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attr -> (UserSessionDto) attr.getAttribute(AttributeConstants.SESSION, RequestAttributes.SCOPE_REQUEST))
                .orElse(null);
    }

    public static Locale getLocale() {
        return getRequestOptional()
                .map(req -> req.getHeader(AttributeConstants.LANGUAGE))
                .map(LocaleUtils::getLocale)
                .orElse(null);
    }

    public static String getOriginDomain() {
        return getRequestOptional()
                .map(RequestContext::getOriginDomain)
                .orElse(null);
    }

    private static String getOriginDomain(HttpServletRequest req) {
        var origin = req.getHeader(AttributeConstants.ORIGIN);
        if (StringUtils.isEmpty(origin)) {
            return req.getHeader(AttributeConstants.REFERER);
        }
        return origin;
    }

    public static String getRequestPath() {
        return getRequestOptional()
                .map(HttpServletRequest::getRequestURI)
                .orElse(null);
    }

    public static void setSession(UserSessionDto session) {
        RequestContextHolder.currentRequestAttributes().setAttribute(AttributeConstants.SESSION, session, RequestAttributes.SCOPE_REQUEST);
    }

    public static HttpServletResponse getResponse() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getResponse)
                .orElse(null);
    }

    public static String getCookie(String cookie) {
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
