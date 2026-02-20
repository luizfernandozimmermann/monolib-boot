package br.com.pokedexerp.platform.authentication.handler.dto;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInOutput {

    private SessionInfo session;

    public SignInOutput(UserSessionDto session) {
        this.session = new SessionInfo(session);
    }
}
