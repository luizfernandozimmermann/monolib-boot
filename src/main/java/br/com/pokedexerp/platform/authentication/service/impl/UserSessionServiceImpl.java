package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.factory.UserSessionFactory;
import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import br.com.pokedexerp.platform.authentication.repository.UserSessionRepository;
import br.com.pokedexerp.platform.authentication.service.CookieService;
import br.com.pokedexerp.platform.authentication.service.UserSessionService;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import br.com.pokedexerp.platform.user.model.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSessionServiceImpl implements UserSessionService {

    UserSessionRepository repository;

    TranslationService translationService;

    UserSessionFactory factory;

    CookieService cookieService;

    @Override
    @Transactional(readOnly = true)
    public UserSessionDto findByAccessToken(String accessToken) {
        return repository.findByAccessTokenHashAndRevokedAtNull(accessToken)
                .map(UserSessionDto::new)
                .orElseThrow(() -> {
                    var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
                    return new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserSessionDto findByRefreshToken(String refreshToken) {
        return repository.findByRefreshTokenHash(refreshToken)
                .map(UserSessionDto::new)
                .orElseThrow(() -> {
                    var errorMessage = translationService.getMessage(TranslationConstants.UNAUTHORIZED);
                    return new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
                });
    }

    @Override
    @Transactional
    public void revokeSession(UUID id) {
        cookieService.clearCookies();
        repository.revokeSession(id, LocalDateTime.now());
    }

    @Override
    @Transactional
    public UserSessionDto createSession(UserDto user) {
        var session = factory.create(user);
        repository.save(session);
        cookieService.setCookies(session);
        return new UserSessionDto(session);
    }

    @Override
    @Transactional
    public UserSessionDto createSessionNotFirst(UserDto user) {
        var session = factory.create(user);
        session.setFirstSession(false);
        repository.save(session);
        cookieService.setCookies(session);
        return new UserSessionDto(session);
    }

    @Override
    @Transactional
    public void revokeAllSessionsByUser(UUID userId) {
        cookieService.clearCookies();
        repository.revokeAllSessionsByUser(userId);
    }

}
