package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.EmptyOutput;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface SignOutHandler {

    @PostRequest(path = "/signOut", authenticateFirstSession = false)
    EmptyOutput signOut();

}
