package br.com.pokedexerp.platform.encrypt.service.impl;

import br.com.pokedexerp.platform.encrypt.service.PasswordEncryptService;
import br.com.pokedexerp.platform.utils.EnvironmentConstants;
import br.com.pokedexerp.platform.utils.EnvironmentUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptServiceImpl implements PasswordEncryptService {

    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536; // 64 MB
    private static final int PARALLELISM = 1;

    private Argon2 argon2;

    private String pepper;

    @PostConstruct
    private void init() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        this.pepper = EnvironmentUtils.getString(EnvironmentConstants.PASSWORD_PEPPER);
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
