package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.UserSessionEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends BaseEntityRepository<UserSessionEntity>, UserSessionCustomRepository {
    Optional<UserSessionEntity> findByAccessTokenHashAndRevokedAtNull(String accessTokenHash);

    Optional<UserSessionEntity> findFirstByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<UserSessionEntity> findByRefreshTokenHash(String refreshTokenHash);
}
