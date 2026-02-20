package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseEntityRepository<RoleEntity> {
    Optional<RoleEntity> findByName(String name);

    Optional<RoleEntity> findByAdminTrue();
}
