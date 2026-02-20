package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.UserCredentialFactory;
import br.com.pokedexerp.platform.authentication.model.UserCredentialEntity;
import br.com.pokedexerp.platform.authentication.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCredentialFactoryImpl implements UserCredentialFactory {

    UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserCredentialEntity create(UUID userId, String password) {
        var credential = new UserCredentialEntity();
        credential.setPasswordHash(password);
        credential.setUser(userService.findById(userId));
        return credential;
    }

}
