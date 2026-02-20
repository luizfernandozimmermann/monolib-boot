package br.com.pokedexerp.platform.authentication.service.impl;

import br.com.pokedexerp.platform.authentication.dto.CreateUserDto;
import br.com.pokedexerp.platform.authentication.factory.UserFactory;
import br.com.pokedexerp.platform.authentication.repository.UserRepository;
import br.com.pokedexerp.platform.authentication.service.UserService;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import br.com.pokedexerp.platform.user.model.UserDto;
import br.com.pokedexerp.platform.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository repository;

    TranslationService translationService;

    UserFactory factory;

    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> {
            var errorMessage = translationService.getMessage(TranslationConstants.USER_NOT_FOUND);
            return new ServiceException(HttpStatus.NOT_FOUND, errorMessage);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsAny() {
        return repository.existsAny();
    }

    @Override
    public UserDto createFirstUser(CreateUserDto dto) {
        var user = factory.create(dto);
        user.setAdmin(true);
        return new UserDto(repository.save(user));
    }

}
