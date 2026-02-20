package br.com.pokedexerp.platform.messaging.context;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.messaging.utils.LocaleUtils;
import br.com.pokedexerp.platform.user.model.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Optional;

@Component
public class ServiceContext {

    @Getter
    private static ApplicationContext applicationContext;

    @Inject
    private ServiceContext(@NonNull ApplicationContext applicationContext) {
        ServiceContext.applicationContext = applicationContext;
    }

    public static Locale getLocale() {
        return Optional.ofNullable(RequestContext.getLocale())
                .or(() -> Optional.ofNullable(WebSocketContext.getLocale()))
                .orElse(LocaleUtils.DEFAULT_LOCALE);
    }

    public static UserDto getUser() {
        return Optional.ofNullable(getSession())
                .map(UserSessionDto::getUser)
                .orElse(null);
    }

    public static String getEmail() {
        return Optional.ofNullable(getUser())
                .map(UserDto::getEmail)
                .orElse(null);
    }

    public static String getOriginDomain() {
        return Optional.ofNullable(RequestContext.getOriginDomain())
                .or(() -> Optional.ofNullable(WebSocketContext.getOriginDomain()))
                .orElse("");
    }

    public static String getRequestPath() {
        return RequestContext.getRequestPath();
    }

    public static UserSessionDto getSession() {
        return Optional.ofNullable(RequestContext.getSession())
                .or(() -> Optional.ofNullable(WebSocketContext.getSession()))
                .orElse(null);
    }

    public static HttpServletResponse getResponse() {
        return RequestContext.getResponse();
    }

    public static String getCookie(String cookie) {
        return RequestContext.getCookie(cookie);
    }

}
