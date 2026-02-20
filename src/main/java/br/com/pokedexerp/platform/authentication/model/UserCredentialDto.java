package br.com.pokedexerp.platform.authentication.model;

import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import br.com.pokedexerp.platform.user.model.UserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserCredentialDto extends BaseEntityDto {

    private UserDto user;

    public UserCredentialDto(UserCredentialEntity userCredential) {
        super(userCredential);
        user = new UserDto(userCredential.getUser());
    }
}
