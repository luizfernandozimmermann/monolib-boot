package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.factory.UserRoleFactory;
import br.com.pokedexerp.platform.authentication.repository.UserRoleRepository;
import br.com.pokedexerp.platform.authentication.service.UserRoleService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleServiceImpl implements UserRoleService {

    UserRoleRepository repository;

    UserRoleFactory factory;

    @Override
    @Transactional(readOnly = true)
    public boolean userHasPermission(UUID userId, String[] roles) {
        return repository.userHasPermission(userId, roles);
    }

    @Override
    @Transactional
    public void grantRole(UUID userId, String role) {
        var userRole = factory.create(userId, role);
        repository.save(userRole);
    }
}
