package monolib.auth.crypto;

public interface PasswordHasher {
    String hash(String value);
    boolean verify(String hash, String value);
}
