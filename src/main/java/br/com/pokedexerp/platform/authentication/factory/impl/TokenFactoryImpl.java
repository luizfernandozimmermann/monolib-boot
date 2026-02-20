package br.com.pokedexerp.platform.authentication.factory.impl;

import br.com.pokedexerp.platform.authentication.factory.TokenFactory;
import br.com.pokedexerp.platform.utils.EnvironmentConstants;
import br.com.pokedexerp.platform.utils.EnvironmentUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Component
public class TokenFactoryImpl implements TokenFactory {

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        var envSecret = EnvironmentUtils.getString(EnvironmentConstants.TOKEN_PEPPER);
        Objects.requireNonNull(envSecret, "Variable AUTHENTICATION_SECRET is required");
        byte[] decodedKey = Base64.getDecoder().decode(envSecret);
        secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    @Override
    public String createAccessToken(String userId, LocalDateTime expiresAt) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(Date.from(expiresAt.toInstant(ZoneOffset.UTC)))
                .signWith(secretKey)
                .compact();
    }

    @Override
    @SneakyThrows
    public String createRefreshToken() {
        var secureRandom = SecureRandom.getInstanceStrong();
        var randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}
