package monolib.auth.credential.service;

import monolib.core.model.Credential;
import monolib.core.model.User;

public interface CredentialService {
    Credential findByEmailAndPassword(String email, String password);

    void changePassword(Credential credential, String newPassword);

    void createCredential(User user, String password);
}
