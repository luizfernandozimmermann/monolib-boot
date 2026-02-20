package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.model.UserSessionEntity;
import br.com.pokedexerp.platform.authentication.service.CookieService;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.messaging.utils.AttributeConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Service
public class CookieServiceImpl implements CookieService {

    private static final String STRICT = "Strict";
    private static final String PATH = "/";

    @Override
    public void setCookies(UserSessionEntity session) {
        var response = ServiceContext.getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        response.addHeader(HttpHeaders.SET_COOKIE, buildAccessToken(session.getAccessTokenHash(), 15, true));
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshToken(session.getRefreshTokenHash(), true));
    }

    @Override
    public void clearCookies() {
        var response = ServiceContext.getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        clearAccess(response);
        clearRefresh(response);
    }

    @Override
    public void clearAccess() {
        var response = ServiceContext.getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        clearAccess(response);
    }

    private static void clearAccess(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, buildAccessToken(Strings.EMPTY, 0, false));
    }

    private static void clearRefresh(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshToken(Strings.EMPTY, false));
    }

    private static String buildAccessToken(String accessToken, int minutes, boolean secure) {
        return ResponseCookie.from(AttributeConstants.COOKIE_TOKEN, accessToken)
                .httpOnly(true)
                .secure(secure)
                .path(PATH)
                .sameSite(STRICT)
                .maxAge(Duration.ofMinutes(minutes))
                .build()
                .toString();
    }

    private static String buildRefreshToken(String refreshToken, boolean secure) {
        return ResponseCookie.from(AttributeConstants.COOKIE_REFRESH, refreshToken)
                .httpOnly(true)
                .secure(secure)
                .path(PATH)
                .sameSite(STRICT)
                .build()
                .toString();
    }

}
