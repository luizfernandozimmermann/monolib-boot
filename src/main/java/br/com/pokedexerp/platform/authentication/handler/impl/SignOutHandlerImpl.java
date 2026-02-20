package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.dto.EmptyOutput;
import br.com.pokedexerp.platform.authentication.handler.SignOutHandler;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@Handler
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignOutHandlerImpl implements SignOutHandler {

    AuthenticationService authenticationService;

    @Override
    public EmptyOutput signOut() {
        authenticationService.signOut();
        return new EmptyOutput();
    }

}
