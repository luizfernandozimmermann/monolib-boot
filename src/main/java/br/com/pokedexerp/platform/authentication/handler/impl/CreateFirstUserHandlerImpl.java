package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.dto.CreateFirstUserInput;
import br.com.pokedexerp.platform.authentication.handler.dto.CreateFirstUserOutput;
import br.com.pokedexerp.platform.authentication.handler.CreateFirstUserHandler;
import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.authentication.service.AuthenticationService;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

@Handler
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateFirstUserHandlerImpl implements CreateFirstUserHandler {

    AuthenticationService authenticationService;

    @Override
    public CreateFirstUserOutput createFirstUser(CreateFirstUserInput input) {
        var dto = CreateUserDto.builder()
                .email(input.getLogin().getEmail())
                .password(input.getLogin().getPassword())
                .username(input.getUsername())
                .build();
        var session = authenticationService.createFirstUser(dto);
        return new CreateFirstUserOutput(session);
    }

}
