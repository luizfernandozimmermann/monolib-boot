package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.UserRoleFactory;
import br.com.pokedexerp.platform.authentication.model.UserRoleEntity;
import br.com.pokedexerp.platform.authentication.service.RoleService;
import br.com.pokedexerp.platform.authentication.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleFactoryImpl implements UserRoleFactory {

    UserService userService;

    RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserRoleEntity create(UUID userId, String role) {
        var userRole = new UserRoleEntity();
        userRole.setUser(userService.findById(userId));
        userRole.setRole(roleService.findByName(role));
        return userRole;
    }

}
