package br.com.pokedexerp.platform.encrypt.service.impl;

import br.com.pokedexerp.platform.encrypt.service.EncryptService;
import br.com.pokedexerp.platform.utils.EnvironmentConstants;
import br.com.pokedexerp.platform.utils.EnvironmentUtils;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Service
class EncryptServiceImpl implements EncryptService {

    private Key key;

    @PostConstruct
    private void init() {
        var pepper = EnvironmentUtils.getString(EnvironmentConstants.TOKEN_PEPPER);
        byte[] decoded = Base64.getDecoder().decode(pepper);
        key = new SecretKeySpec(decoded, "HmacSHA256");
    }

    @Override
    @SneakyThrows
    public String hash(String value) {
        var mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] hash = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
