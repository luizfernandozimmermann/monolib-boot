package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.SignInInput;
import br.com.pokedexerp.platform.authentication.handler.dto.SignInOutput;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface SignInHandler {

    @PostRequest(path = "/signIn", authenticate = false)
    SignInOutput signIn(SignInInput input);

}
