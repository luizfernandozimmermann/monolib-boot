package monolib.data.domain.usercredential.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Credential;
import monolib.data.domain.user.converter.UserConverter;
import monolib.data.domain.user.repository.UserRepository;
import monolib.data.domain.usercredential.model.UserCredentialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCredentialConverterImpl implements UserCredentialConverter {

    UserRepository userRepository;

    @Override
    public Credential convert(UserCredentialEntity entity) {
        var credential = new Credential();
        credential.setId(entity.getId());
        credential.setPassword(entity.getPasswordHash());
        credential.setUser(UserConverter.convert(entity.getUser()));
        return credential;
    }

    @Override
    @Transactional(readOnly = true)
    public UserCredentialEntity convert(UserCredentialEntity entity, Credential credential) {
        entity.setId(credential.getId());
        entity.setPasswordHash(credential.getPassword());
        userRepository.findById(credential.getUser().getId()).ifPresent(entity::setUser);
        return entity;
    }

}
