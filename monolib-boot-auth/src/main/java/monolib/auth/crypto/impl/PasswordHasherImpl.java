package monolib.auth.crypto.impl;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.annotation.PostConstruct;
import monolib.auth.crypto.PasswordHasher;
import monolib.auth.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordHasherImpl implements PasswordHasher {

    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536; // 64 MB
    private static final int PARALLELISM = 1;

    @Autowired
    private AuthProperties properties;

    private Argon2 argon2;

    private String pepper;

    @PostConstruct
    public void init() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        pepper = properties.getPasswordToken();
    }

    @Override
    public String hash(String value) {
        char[] arrayValue = (value + pepper).toCharArray();
        try {
            return argon2.hash(
                    ITERATIONS,
                    MEMORY,
                    PARALLELISM,
                    arrayValue
            );
        } finally {
            argon2.wipeArray(arrayValue);
        }
    }

    @Override
    public boolean verify(String hash, String value) {
        char[] arrayValue = (value + pepper).toCharArray();
        try {
            return argon2.verify(hash, arrayValue);
        } finally {
            argon2.wipeArray(arrayValue);
        }
    }

}
