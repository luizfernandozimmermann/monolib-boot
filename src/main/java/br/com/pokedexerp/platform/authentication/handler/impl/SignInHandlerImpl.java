package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.dto.SignInInput;
import br.com.pokedexerp.platform.authentication.handler.dto.SignInOutput;
import br.com.pokedexerp.platform.authentication.handler.SignInHandler;
import br.com.pokedexerp.platform.authentication.dto.SignInDto;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@Handler
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignInHandlerImpl implements SignInHandler {

    AuthenticationService authenticationService;

    @Override
    public SignInOutput signIn(SignInInput input) {
        var dto = SignInDto.builder()
                .email(input.getLogin().getEmail())
                .password(input.getLogin().getPassword())
                .build();
        var session = authenticationService.signIn(dto);
        return new SignInOutput(session);
    }

}
