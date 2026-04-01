package monolib.web.ready.authentication.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.context.ContextHolder;
import monolib.core.service.AuthenticationCoreService;
import monolib.web.api.Handler;
import monolib.web.api.PostRequest;
import monolib.web.context.RequestContext;
import monolib.web.cookie.CookieService;
import monolib.web.ready.authentication.handler.dto.RefreshSessionOutput;
import monolib.web.utils.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Handler(path = "authentication")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshSessionHandler {

    AuthenticationCoreService authenticationService;

    CookieService cookieService;

    @PostRequest(path = "/refreshSession", authenticate = false)
    public RefreshSessionOutput refreshSession() {
        var context = (RequestContext) ContextHolder.get();
        var session = authenticationService.refreshSession(context.getCookie(AttributeConstants.REFRESH_COOKIE));
        cookieService.setCookies(session);
        log.info("Session refreshed for {}", session.getUser().getEmail());
        return new RefreshSessionOutput(session);
    }

}
