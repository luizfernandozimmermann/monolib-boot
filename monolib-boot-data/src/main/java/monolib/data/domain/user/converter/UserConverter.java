package monolib.data.domain.user.converter;

import lombok.experimental.UtilityClass;
import monolib.core.model.User;
import monolib.data.domain.user.model.UserEntity;

@UtilityClass
public class UserConverter {

    public static User convert(UserEntity entity) {
        var user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setAdmin(entity.isAdmin());
        return user;
    }

    public static UserEntity convert(UserEntity entity, User user) {
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setAdmin(user.isAdmin());
        return entity;
    }
}
