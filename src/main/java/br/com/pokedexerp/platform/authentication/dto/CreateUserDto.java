package br.com.pokedexerp.platform.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserDto {

    private String email;
    private String password;
    private String username;

}
