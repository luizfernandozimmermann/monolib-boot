package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.dto.ChangePasswordInput;
import br.com.pokedexerp.platform.authentication.handler.dto.ChangePasswordOutput;
import br.com.pokedexerp.platform.authentication.handler.ChangePasswordHandler;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@Handler
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangePasswordHandlerImpl implements ChangePasswordHandler {

    AuthenticationService authenticationService;

    @Override
    public ChangePasswordOutput changePassword(ChangePasswordInput input) {
        var session = authenticationService.changePassword(input.getOldPassword(), input.getNewPassword());
        return new ChangePasswordOutput(session);
    }

}
