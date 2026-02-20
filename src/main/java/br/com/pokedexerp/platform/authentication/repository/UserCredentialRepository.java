package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.UserCredentialEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends BaseEntityRepository<UserCredentialEntity> {
    Optional<UserCredentialEntity> findByUserEmail(String email);
}
