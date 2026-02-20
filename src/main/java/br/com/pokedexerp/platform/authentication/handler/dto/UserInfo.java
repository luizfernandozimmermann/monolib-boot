package br.com.pokedexerp.platform.authentication.handler.dto;

import br.com.pokedexerp.platform.user.model.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserInfo {

    private UUID id;

    private String name;

    private String email;

    public UserInfo(UserDto user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
    }
}
