package monolib.auth.credential.service.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.auth.credential.service.CredentialService;
import monolib.auth.crypto.PasswordHasher;
import monolib.auth.utils.TranslationConstants;
import monolib.core.model.Credential;
import monolib.core.repository.CredentialCoreRepository;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationService;
import monolib.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialServiceImpl implements CredentialService {

    CredentialCoreRepository repository;

    TranslationService translationService;

    PasswordHasher passwordHasher;

    @Override
    @Transactional(readOnly = true)
    public Credential findByEmailAndPassword(String email, String password) {
        return repository.findByUserEmail(email)
                .filter(credential -> passwordHasher.verify(credential.getPassword(), password))
                .orElseThrow(() -> {
                    var errorMessage = translationService.getMessage(TranslationConstants.INVALID_CREDENTIALS);
                    return new BusinessException(ErrorCode.UNAUTHORIZED, errorMessage);
                });
    }

    @Override
    @Transactional
    public void changePassword(Credential credential, String newPassword) {
        credential.setPassword(passwordHasher.hash(newPassword));
        repository.save(credential);
    }

    @Override
    @Transactional
    public void createCredential(User user, String password) {
        var credential = new Credential();
        credential.setUser(user);
        credential.setPassword(passwordHasher.hash(password));
        repository.save(credential);
    }

}
