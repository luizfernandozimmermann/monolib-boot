package br.com.pokedexerp.platform.authentication.repository;

import br.com.pokedexerp.platform.authentication.model.PermissionEntity;
import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.model.RolePermissionEntity;
import br.com.pokedexerp.platform.entity.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends BaseEntityRepository<RolePermissionEntity> {
    boolean existsByRoleAndPermission(RoleEntity role, PermissionEntity permission);
}
