package monolib.data.domain.usercredential.converter;

import monolib.core.model.Credential;
import monolib.data.domain.usercredential.model.UserCredentialEntity;

public interface UserCredentialConverter {
    Credential convert(UserCredentialEntity entity);
    UserCredentialEntity convert(UserCredentialEntity entity, Credential credential);
}
