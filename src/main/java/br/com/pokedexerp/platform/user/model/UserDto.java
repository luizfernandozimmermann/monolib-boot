package br.com.pokedexerp.platform.user.model;

import br.com.pokedexerp.platform.entity.model.BaseEntityDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseEntityDto {

    private String name;

    private String email;

    public UserDto(UserEntity user) {
        super(user);
        name = user.getName();
        email = user.getEmail();
    }

    public UserDto(UUID id) {
        super();
        this.id = id;
    }
}
