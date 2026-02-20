package br.com.pokedexerp.platform.messaging.context;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.messaging.utils.AttributeConstants;
import br.com.pokedexerp.platform.messaging.utils.LocaleUtils;
import lombok.experimental.UtilityClass;
import org.springframework.web.socket.WebSocketSession;

import java.util.Locale;
import java.util.Optional;

@UtilityClass
public class WebSocketContext {

    private static final ThreadLocal<WebSocketSession> sessionHolder = new ThreadLocal<>();

    public static void setWebsocketSession(WebSocketSession session) {
        sessionHolder.set(session);
    }

    public static WebSocketSession getWebsocketSession() {
        return sessionHolder.get();
    }

    public static Locale getLocale() {
        return Optional.ofNullable(getWebsocketSession())
                .map(WebSocketSession::getAttributes)
                .map(attr -> (String) attr.get(AttributeConstants.LANGUAGE))
                .map(LocaleUtils::getLocale)
                .orElse(null);
    }

    public static UserSessionDto getSession() {
        return Optional.ofNullable(getWebsocketSession())
                .map(WebSocketSession::getAttributes)
                .map(attr -> (UserSessionDto) attr.get(AttributeConstants.SESSION))
                .orElse(null);
    }

    public static void clear() {
        sessionHolder.remove();
    }

    public static String getOriginDomain() {
        return Optional.ofNullable(getWebsocketSession())
                .map(WebSocketSession::getHandshakeHeaders)
                .map(headers -> headers.getFirst(AttributeConstants.ORIGIN))
                .orElse(null);
    }

}
