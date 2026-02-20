package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.UserRoleEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends BaseEntityRepository<UserRoleEntity>, UserRoleCustomRepository {
}
