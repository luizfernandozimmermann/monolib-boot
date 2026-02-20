package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.user.model.UserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserSessionDto extends BaseEntityDto {

    private UserDto user;

    private LocalDateTime expiresAt;

    private LocalDateTime revokedAt;

    private boolean firstSession;

    public UserSessionDto(UserSessionEntity session) {
        super(session);
        user = new UserDto(session.getUser());
        expiresAt = session.getExpiresAt();
        revokedAt = session.getRevokedAt();
        firstSession = session.isFirstSession();
    }

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean isRevoked() {
        return revokedAt != null;
    }
}
