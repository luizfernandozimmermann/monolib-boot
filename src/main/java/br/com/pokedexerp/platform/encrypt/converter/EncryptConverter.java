package br.com.pokedexerp.platform.encrypt.converter;

import br.com.pokedexerp.platform.encrypt.service.EncryptService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EncryptConverter implements AttributeConverter<String, String> {

    EncryptService encryptService;

    @Override
    public String convertToDatabaseColumn(String value) {
        return encryptService.hash(value);
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return value;
    }

}
