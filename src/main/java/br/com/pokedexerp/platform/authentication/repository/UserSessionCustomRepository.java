package br.com.pokedexerp.platform.authentication.repository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserSessionCustomRepository {
    void revokeSession(UUID id, LocalDateTime dateTime);

    void revokeAllSessionsByUser(UUID userId);
}
