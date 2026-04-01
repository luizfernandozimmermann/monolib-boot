package monolib.web.ready.authentication.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.context.ContextHolder;
import monolib.core.service.AuthenticationCoreService;
import monolib.web.api.Handler;
import monolib.web.api.PostRequest;
import monolib.web.cookie.CookieService;
import monolib.web.ready.authentication.handler.dto.EmptyOutput;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(path = "authentication")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignOutHandler {

    AuthenticationCoreService authenticationService;

    CookieService cookieService;

    @PostRequest(path = "/signOut", authenticateFirstSession = false)
    public EmptyOutput signOut() {
        var session = ContextHolder.get().getSession();
        authenticationService.signOut(session);
        cookieService.clearCookies();
        return new EmptyOutput();
    }

}
