package br.com.pokedexerp.platform.authentication.handler.dto;

import br.com.pokedexerp.platform.authentication.model.UserSessionDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionInfo {

    private UserInfo user;

    private LocalDateTime expiresAt;

    private boolean firstSession;

    public SessionInfo(UserSessionDto session) {
        user = new UserInfo(session.getUser());
        expiresAt = session.getExpiresAt();
        firstSession = session.isFirstSession();
    }
}
