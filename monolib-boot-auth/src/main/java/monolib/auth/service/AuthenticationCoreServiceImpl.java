package monolib.auth.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.credential.service.CredentialService;
import monolib.auth.session.service.SessionService;
import monolib.auth.user.service.UserService;
import monolib.auth.validator.AuthenticationValidator;
import monolib.core.model.Role;
import monolib.core.model.Session;
import monolib.core.model.User;
import monolib.core.service.AuthenticationCoreService;
import monolib.core.service.RoleCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationCoreServiceImpl implements AuthenticationCoreService {

    private static final Role ROLE_ADMIN = Role.of("ADMIN");

    SessionService sessionService;

    CredentialService credentialService;

    UserService userService;

    RoleCoreService roleService;

    AuthenticationValidator validator;

    @Override
    @Transactional
    public Session authenticate(String accessToken) {
        var session = sessionService.findByAccessToken(accessToken);
        validator.validateNotExpired(session);
        return session;
    }

    @Override
    @Transactional
    public Session signIn(String email, String password) {
        var user = credentialService.findByEmailAndPassword(email, password).getUser();
        return sessionService.createSession(user);
    }

    @Override
    @Transactional
    public Session changePassword(User user, String oldPassword, String newPassword) {
        var credential = credentialService.findByEmailAndPassword(user.getEmail(), oldPassword);
        credentialService.changePassword(credential, newPassword);
        sessionService.revokeAllUserSessions(credential.getUser());
        return sessionService.createSession(credential.getUser(), false);
    }

    @Override
    @Transactional
    public Session createFirstUser(String email, String password, String username) {
        validator.validateCreateFirstUser();
        var user = userService.createFirstUser(email, username);
        credentialService.createCredential(user, password);
        roleService.grantRole(user, ROLE_ADMIN);
        return sessionService.createSession(user);
    }

    @Override
    @Transactional
    public Session refreshSession(String refreshToken) {
        validator.validateTokenInformed(refreshToken);
        var session = sessionService.findByRefreshToken(refreshToken);
        validator.validateNotRevoked(session);
        sessionService.revokeSession(session);
        return sessionService.createSession(session.getUser());
    }

    @Override
    @Transactional
    public void signOut(Session session) {
        sessionService.revokeSession(session);
    }

}
