package monolib.web.ready.authentication.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.service.AuthenticationCoreService;
import monolib.web.api.Handler;
import monolib.web.api.PostRequest;
import monolib.web.cookie.CookieService;
import monolib.web.ready.authentication.handler.dto.CreateFirstUserInput;
import monolib.web.ready.authentication.handler.dto.CreateFirstUserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Handler(path = "authentication")
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ConditionalOnProperty(name = "monolib.web.starter.bootstrap", havingValue = "true")
public class CreateFirstUserHandler {

    AuthenticationCoreService authenticationService;

    CookieService cookieService;

    @PostRequest(path = "/createFirstUser", authenticate = false)
    public CreateFirstUserOutput createFirstUser(CreateFirstUserInput input) {
        var login = input.getLogin();
        var session = authenticationService.createFirstUser(login.getEmail(), login.getPassword(), input.getUsername());
        cookieService.setCookies(session);
        return new CreateFirstUserOutput(session);
    }

}
