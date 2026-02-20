package br.com.pokedexerp.platform.authentication.handler.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFirstUserInput {

    @NotNull @Size(min = 3, max = 30)
    private String username;

    @NotNull @Valid
    private LoginInfo login;

}
