package monolib.auth.validator;

import monolib.core.model.Session;
import monolib.core.validator.AuthenticationCoreValidator;

public interface AuthenticationValidator extends AuthenticationCoreValidator {
    /**
     * Validates if the given session is not expired
     * @param session Session to be validated
     */
    void validateNotExpired(Session session);

    /**
     * Validates if the given token is informed
     * @param token Token to be validated
     */
    void validateTokenInformed(String token);

    /**
     * Validates if the given session is not revoked
     * @param session Session to be validated
     */
    void validateNotRevoked(Session session);

    /**
     * Validates if it's valid to create the first user of the application
     */
    void validateCreateFirstUser();
}
