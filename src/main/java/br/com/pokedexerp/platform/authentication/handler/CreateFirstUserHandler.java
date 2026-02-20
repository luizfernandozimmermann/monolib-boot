package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.CreateFirstUserInput;
import br.com.pokedexerp.platform.authentication.handler.dto.CreateFirstUserOutput;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface CreateFirstUserHandler {

    @PostRequest(path = "/createFirstUser", authenticate = false)
    CreateFirstUserOutput createFirstUser(CreateFirstUserInput input);

}
