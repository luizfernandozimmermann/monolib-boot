package monolib.web.ready.authentication.handler;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.service.AuthenticationCoreService;
import monolib.web.api.Handler;
import monolib.web.api.PostRequest;
import monolib.web.cookie.CookieService;
import monolib.web.ready.authentication.handler.dto.SignInInput;
import monolib.web.ready.authentication.handler.dto.SignInOutput;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(path = "authentication")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignInHandler {

    AuthenticationCoreService authenticationService;

    CookieService cookieService;

    @PostRequest(path = "/signIn", authenticate = false)
    public SignInOutput signIn(@Valid SignInInput input) {
        var login = input.getLogin();
        var session = authenticationService.signIn(login.getEmail(), login.getPassword());
        cookieService.setCookies(session);
        return new SignInOutput(session);
    }

}
