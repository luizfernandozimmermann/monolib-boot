package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.RefreshSessionHandler;
import br.com.pokedexerp.platform.authentication.handler.dto.RefreshSessionOutput;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Handler
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshSessionHandlerImpl implements RefreshSessionHandler {

    AuthenticationService authenticationService;

    @Override
    public RefreshSessionOutput refreshSession() {
        var session = authenticationService.refreshSession();
        log.info("Session refreshed for {}", session.getUser().getEmail());
        return new RefreshSessionOutput(session);
    }

}
