package monolib.web.ready.records.user.validator;


import monolib.core.model.User;
import monolib.data.domain.user.model.UserEntity;

import java.util.UUID;

public interface UserValidator {
    void validateSameUserOrAdmin(UserEntity user, User userRequest);

    void validateIsNotAdmin(UserEntity user);

    void validateNotSameUser(UserEntity user, UUID anotherUserId);
}
