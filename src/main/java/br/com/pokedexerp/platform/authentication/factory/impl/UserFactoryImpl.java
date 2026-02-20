package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.authentication.factory.UserFactory;
import br.com.pokedexerp.platform.user.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFactoryImpl implements UserFactory {

    @Override
    public UserEntity create(CreateUserDto dto) {
        var user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setName(dto.getUsername());
        return user;
    }

}
