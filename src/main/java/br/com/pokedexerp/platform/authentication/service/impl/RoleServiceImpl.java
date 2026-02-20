package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.model.RoleEntity;
import br.com.pokedexerp.platform.authentication.repository.RoleRepository;
import br.com.pokedexerp.platform.authentication.service.RoleService;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository repository;

    TranslationService translationService;

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> {
            var errorMessage = translationService.getMessage(TranslationConstants.ROLE_NOT_FOUND);
            return new ServiceException(HttpStatus.NOT_FOUND, errorMessage);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleEntity> findByNameOpt(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional
    public RoleEntity save(RoleEntity role) {
        return repository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleEntity findAdmin() {
        return repository.findByAdminTrue().orElseThrow(() -> {
            var errorMessage = translationService.getMessage(TranslationConstants.ROLE_NOT_FOUND);
            return new ServiceException(HttpStatus.NOT_FOUND, errorMessage);
        });
    }

}
