package br.com.pokedexerp.platform.authentication.handler;

import br.com.pokedexerp.platform.authentication.handler.dto.GetSessionInfoOutput;
import br.com.pokedexerp.platform.messaging.annotation.GetRequest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("authentication")
public interface GetSessionInfoHandler {

    @GetRequest(path = "/getSessionInfo", authenticateFirstSession = false)
    GetSessionInfoOutput getSessionInfo();

}
