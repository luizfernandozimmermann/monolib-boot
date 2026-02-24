package monolib.auth.crypto;

import monolib.core.model.User;

import java.time.LocalDateTime;

public interface SessionHasher {

    String createAccessToken(User user, LocalDateTime expiresAt);
    String createRefreshToken();
    String hash(String value);

}
