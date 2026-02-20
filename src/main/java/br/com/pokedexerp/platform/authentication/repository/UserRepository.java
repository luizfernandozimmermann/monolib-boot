package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import br.com.pokedexerp.platform.user.model.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseEntityRepository<UserEntity>, UserCustomRepository {
}
