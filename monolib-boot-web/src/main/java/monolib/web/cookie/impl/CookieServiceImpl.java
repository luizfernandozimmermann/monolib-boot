package monolib.web.cookie.impl;

import jakarta.servlet.http.HttpServletResponse;
import monolib.core.context.ContextHolder;
import monolib.core.model.Session;
import monolib.web.context.RequestContext;
import monolib.web.cookie.CookieService;
import monolib.web.utils.AttributeConstants;
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
    public void setCookies(Session session) {
        var response = getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        response.addHeader(HttpHeaders.SET_COOKIE, buildAccessToken(session.getAccessToken(), 15, true));
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshToken(session.getRefreshToken(), true));
    }

    @Override
    public void clearCookies() {
        var response = getResponse();
        if (Objects.isNull(response)) {
            return;
        }
        clearAccess(response);
        clearRefresh(response);
    }

    @Override
    public void clearAccess() {
        var response = getResponse();
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
        return ResponseCookie.from(AttributeConstants.ACCESS_COOKIE, accessToken)
                .httpOnly(true)
                .secure(secure)
                .path(PATH)
                .sameSite(STRICT)
                .maxAge(Duration.ofMinutes(minutes))
                .build()
                .toString();
    }

    private static String buildRefreshToken(String refreshToken, boolean secure) {
        return ResponseCookie.from(AttributeConstants.REFRESH_COOKIE, refreshToken)
                .httpOnly(true)
                .secure(secure)
                .path(PATH)
                .sameSite(STRICT)
                .build()
                .toString();
    }

    private static HttpServletResponse getResponse() {
        return ((RequestContext) ContextHolder.get()).getResponse();
    }

}
