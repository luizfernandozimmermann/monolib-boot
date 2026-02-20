package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.authentication.dto.SignInDto;
import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.authentication.service.UserCredentialService;
import br.com.pokedexerp.platform.authentication.service.UserRoleService;
import br.com.pokedexerp.platform.authentication.service.UserService;
import br.com.pokedexerp.platform.authentication.service.UserSessionService;
import br.com.pokedexerp.platform.authentication.validator.AuthenticationValidator;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.messaging.utils.AttributeConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String ROLE_ADMIN = "ADMIN";

    UserSessionService sessionService;

    UserCredentialService credentialService;

    UserService userService;

    UserRoleService userRoleService;

    AuthenticationValidator validator;

    @Override
    @Transactional
    public UserSessionDto authenticateByAccessToken(String accessToken) {
        var session = sessionService.findByAccessToken(accessToken);
        validator.validateNotExpired(session);
        return session;
    }

    @Override
    @Transactional
    public UserSessionDto signIn(SignInDto dto) {
        var user = credentialService.findByEmailAndPassword(dto.getEmail(), dto.getPassword()).getUser();
        return sessionService.createSession(user);
    }

    @Override
    @Transactional
    public UserSessionDto changePassword(String oldPassword, String newPassword) {
        var credential = credentialService.findByEmailAndPassword(ServiceContext.getEmail(), oldPassword);
        credentialService.changePassword(credential.getId(), newPassword);
        sessionService.revokeAllSessionsByUser(credential.getUser().getId());
        return sessionService.createSessionNotFirst(credential.getUser());
    }

    @Override
    @Transactional
    public UserSessionDto createFirstUser(CreateUserDto dto) {
        validator.validateCreateFirstUser();
        var user = userService.createFirstUser(dto);
        credentialService.createCredential(user.getId(), dto.getPassword());
        userRoleService.grantRole(user.getId(), ROLE_ADMIN);
        return sessionService.createSession(user);
    }

    @Override
    @Transactional
    public UserSessionDto refreshSession() {
        var refreshToken = ServiceContext.getCookie(AttributeConstants.COOKIE_REFRESH);
        validator.validateToken(refreshToken);
        var session = sessionService.findByRefreshToken(refreshToken);
        validator.validateNotRevoked(session);
        sessionService.revokeSession(session.getId());
        return sessionService.createSession(session.getUser());
    }

    @Override
    @Transactional
    public void signOut() {
        sessionService.revokeSession(ServiceContext.getSession().getId());
    }

}
