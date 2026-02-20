package br.com.pokedexerp.platform.authentication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInDto {

    private String email;
    private String password;

}
