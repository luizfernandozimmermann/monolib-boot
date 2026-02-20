package br.com.pokedexerp.platform.authentication.handler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {

    @NotNull
    @Size(min = 10, max = 100)
    private String email;

    @NotNull @Size(min = 8, max = 100)
    private String password;

}
