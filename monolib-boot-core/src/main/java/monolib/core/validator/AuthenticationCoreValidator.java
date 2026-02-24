package monolib.core.validator;

import monolib.core.model.Session;

public interface AuthenticationCoreValidator {
    /**
     * Validates if the given session is the first session of the user after creation
     * @param session Session to be validated
     */
    void validateIsNotFirstSession(Session session);

    /**
     * Validates if the given session is informed
     * @param session Session to be validated
     */
    void validateSessionInformed(Session session);
}
