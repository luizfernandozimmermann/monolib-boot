package monolib.web.ready.authentication.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.context.ContextHolder;
import monolib.core.service.AuthenticationCoreService;
import monolib.web.api.Handler;
import monolib.web.api.PostRequest;
import monolib.web.cookie.CookieService;
import monolib.web.ready.authentication.handler.dto.ChangePasswordInput;
import monolib.web.ready.authentication.handler.dto.ChangePasswordOutput;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(path = "authentication")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangePasswordHandler {

    AuthenticationCoreService authenticationService;

    CookieService cookieService;

    @PostRequest(path = "/changePassword", authenticateFirstSession = false)
    public ChangePasswordOutput changePassword(ChangePasswordInput input) {
        var user = ContextHolder.get().getUser();
        var session = authenticationService.changePassword(user, input.getOldPassword(), input.getNewPassword());
        cookieService.setCookies(session);
        return new ChangePasswordOutput(session);
    }

}
