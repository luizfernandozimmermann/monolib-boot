package monolib.auth.crypto.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import monolib.auth.crypto.SessionHasher;
import monolib.auth.properties.AuthProperties;
import monolib.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

@Component
public class SessionHasherImpl implements SessionHasher {

    private SecretKey secretKey;

    @Autowired
    private AuthProperties properties;

    @PostConstruct
    private void init() {
        byte[] decodedKey = Base64.getDecoder().decode(properties.getSessionToken());
        secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    @Override
    public String createAccessToken(User user, LocalDateTime expiresAt) {
        return Jwts.builder()
                .subject(user.getId().toString())
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

    @Override
    @SneakyThrows
    public String hash(String value) {
        var mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] hash = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
