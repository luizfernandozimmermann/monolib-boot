package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.factory.UserCredentialFactory;
import br.com.pokedexerp.platform.authentication.model.UserCredentialDto;
import br.com.pokedexerp.platform.authentication.repository.UserCredentialRepository;
import br.com.pokedexerp.platform.authentication.service.UserCredentialService;
import br.com.pokedexerp.platform.encrypt.service.PasswordEncryptService;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCredentialServiceImpl implements UserCredentialService {

    UserCredentialRepository repository;

    TranslationService translationService;

    UserCredentialFactory factory;

    PasswordEncryptService passwordEncryptService;

    @Override
    @Transactional(readOnly = true)
    public UserCredentialDto findByEmailAndPassword(String email, String password) {
        return repository.findByUserEmail(email)
                .filter(user -> passwordEncryptService.verify(user.getPasswordHash(), password))
                .map(UserCredentialDto::new)
                .orElseThrow(() -> {
                    var errorMessage = translationService.getMessage(TranslationConstants.INVALID_CREDENTIALS);
                    return new ServiceException(HttpStatus.UNAUTHORIZED, errorMessage);
                });
    }

    @Override
    @Transactional
    public void changePassword(UUID credentialId, String newPassword) {
        repository.findById(credentialId).ifPresent(credential -> {
            credential.setPasswordHash(newPassword);
            repository.save(credential);
        });
    }

    @Override
    @Transactional
    public void createCredential(UUID userId, String password) {
        var credential = factory.create(userId, password);
        repository.save(credential);
    }

}
