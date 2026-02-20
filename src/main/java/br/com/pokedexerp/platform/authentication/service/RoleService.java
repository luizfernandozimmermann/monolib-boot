package br.com.pokedexerp.platform.authentication.service;

import br.com.pokedexerp.platform.authentication.model.RoleEntity;

import java.util.Optional;

public interface RoleService {
    RoleEntity findByName(String name);
    Optional<RoleEntity> findByNameOpt(String name);

    RoleEntity save(RoleEntity role);

    RoleEntity findAdmin();
}
