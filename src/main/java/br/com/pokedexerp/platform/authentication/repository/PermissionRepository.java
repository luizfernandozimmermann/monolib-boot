package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseEntityRepository<PermissionEntity> {
    Optional<PermissionEntity> findByResource(String resource);
}
