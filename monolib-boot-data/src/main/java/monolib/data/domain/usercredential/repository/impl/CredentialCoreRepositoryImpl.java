package monolib.data.domain.usercredential.repository.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.model.Credential;
import monolib.core.repository.CredentialCoreRepository;
import monolib.data.domain.usercredential.converter.UserCredentialConverter;
import monolib.data.domain.usercredential.model.UserCredentialEntity;
import monolib.data.domain.usercredential.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialCoreRepositoryImpl implements CredentialCoreRepository {

    UserCredentialRepository repository;

    UserCredentialConverter converter;

    @Override
    public Optional<Credential> findByUserEmail(String email) {
        return repository.findByUserEmail(email).map(converter::convert);
    }

    @Override
    public Credential save(Credential credential) {
        var entity = Optional.ofNullable(credential.getId())
                .flatMap(repository::findById)
                .orElseGet(UserCredentialEntity::new);
        return converter.convert(repository.save(converter.convert(entity, credential)));
    }
}
