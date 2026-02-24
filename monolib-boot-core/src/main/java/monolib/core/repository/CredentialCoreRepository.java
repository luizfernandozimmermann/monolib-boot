package monolib.core.repository;

import monolib.core.model.Credential;

import java.util.Optional;

public interface CredentialCoreRepository {
    Optional<Credential> findByUserEmail(String email);

    Credential save(Credential credential);
}
