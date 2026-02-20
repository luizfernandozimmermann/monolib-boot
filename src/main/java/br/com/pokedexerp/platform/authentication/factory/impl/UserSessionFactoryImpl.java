package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.TokenFactory;
import br.com.pokedexerp.platform.authentication.factory.UserSessionFactory;
import br.com.pokedexerp.platform.authentication.model.UserSessionEntity;
import br.com.pokedexerp.platform.authentication.repository.UserSessionRepository;
import br.com.pokedexerp.platform.authentication.service.UserService;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.utils.EnvironmentConstants;
import br.com.pokedexerp.platform.utils.EnvironmentUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSessionFactoryImpl implements UserSessionFactory {

    private static final Long DEFAULT_EXPIRATION_TIME = 900L;

    UserService userService;

    TokenFactory tokenFactory;

    UserSessionRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserSessionEntity create(UserDto user) {
        var session = new UserSessionEntity();
        session.setUser(userService.findById(user.getId()));
        session.setExpiresAt(getExpirationDate());
        session.setAccessTokenHash(tokenFactory.createAccessToken(user.getId().toString(), session.getExpiresAt()));
        session.setRefreshTokenHash(tokenFactory.createRefreshToken());
        session.setFirstSession(isFirstSession(user));
        return session;
    }

    private boolean isFirstSession(UserDto user) {
        return repository.findFirstByUserIdOrderByCreatedAtDesc(user.getId())
                .map(UserSessionEntity::isFirstSession)
                .orElse(true);
    }

    private static LocalDateTime getExpirationDate() {
        var expirationTime = EnvironmentUtils.getLong(EnvironmentConstants.SESSION_TOKEN_EXPIRATION_TIME, DEFAULT_EXPIRATION_TIME);
        return LocalDateTime.now().plusSeconds(expirationTime);
    }

}
