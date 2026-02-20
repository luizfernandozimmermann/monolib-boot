package br.com.pokedexerp.platform.authentication.handler.impl;

import br.com.pokedexerp.platform.authentication.handler.dto.GetSessionInfoOutput;
import br.com.pokedexerp.platform.authentication.handler.GetSessionInfoHandler;
import br.com.pokedexerp.platform.messaging.annotation.Handler;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;

@Handler
public class GetSessionInfoHandlerImpl implements GetSessionInfoHandler {

    @Override
    public GetSessionInfoOutput getSessionInfo() {
        return new GetSessionInfoOutput(ServiceContext.getSession());
    }

}
