package monolib.core.service;

import monolib.core.model.Session;
import monolib.core.model.User;

public interface AuthenticationCoreService {
    /**
     * Authenticates the user with the given access token
     * @param accessToken Session access token
     * @return {@link Session} User session
     */
    Session authenticate(String accessToken);

    /**
     * Creates a new session for the user that matches with the given email and password
     * @param email Credential email
     * @param password Credential password
     * @return {@link Session} User session
     */
    Session signIn(String email, String password);

    /**
     * Changes password to the given user and creates a new session
     * @param user {@link User} User to be changing password
     * @param oldPassword User's old password
     * @param newPassword User's new password
     * @return {@link Session} User session
     */
    Session changePassword(User user, String oldPassword, String newPassword);

    /**
     * Creates the first user of the application and creates a new session
     * @param email User's email
     * @param password User's password
     * @param username User's name
     * @return {@link Session} User session
     */
    Session createFirstUser(String email, String password, String username);

    /**
     * Creates a new session using the refresh token
     * @param refreshToken Session refresh token
     * @return {@link Session} User session
     */
    Session refreshSession(String refreshToken);

    /**
     * Invalidates a session
     * @param session {@link Session} User session
     */
    void signOut(Session session);
}
