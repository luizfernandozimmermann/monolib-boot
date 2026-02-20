package br.com.pokedexerp.platform.custom.runner.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolePermissionSeed {

    private List<String> roles;
    private List<PermissionNode> permissions;

}
