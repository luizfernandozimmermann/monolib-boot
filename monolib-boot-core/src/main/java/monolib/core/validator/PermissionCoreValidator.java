package monolib.core.validator;

import monolib.core.model.User;

public interface PermissionCoreValidator {
    /**
     * Validates if the given user has the given permissions
     * @param user {@link User} User to be validated
     * @param permissions Permissions to be validated
     */
    void validate(User user, String[] permissions);
}
