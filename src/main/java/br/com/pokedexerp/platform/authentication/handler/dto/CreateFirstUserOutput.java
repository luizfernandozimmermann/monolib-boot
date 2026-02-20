package br.com.pokedexerp.platform.authentication.handler.dto;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFirstUserOutput {

    private SessionInfo session;

    public CreateFirstUserOutput(UserSessionDto session) {
        this.session = new SessionInfo(session);
    }
}
