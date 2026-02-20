package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.RefreshSessionOutput;
import br.com.pokedexerp.platform.messaging.annotation.PostRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface RefreshSessionHandler {

    @PostRequest(path = "/refreshSession", authenticate = false)
    RefreshSessionOutput refreshSession();

}
