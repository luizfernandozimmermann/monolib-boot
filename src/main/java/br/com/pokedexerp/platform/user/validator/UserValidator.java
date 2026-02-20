package br.com.pokedexerp.platform.user.validator;

import br.com.pokedexerp.platform.user.model.UserEntity;

import java.util.UUID;

public interface UserValidator {
    void validateSameUserOrAdmin(UserEntity user, UUID userRequestId);

    void validateIsNotAdmin(UserEntity user);

    void validateNotSameUser(UserEntity user, UUID anotherUserId);
}
