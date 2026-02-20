package br.com.pokedexerp.platform.translation.service.impl;

import br.com.pokedexerp.platform.messaging.context.RequestContext;
import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TranslationServiceImpl implements TranslationService {

    MessageSource messageSource;

    @Override
    public String getMessage(String key) {
        var locale = ServiceContext.getLocale();
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (Exception _) {
            return key;
        }
    }

    @Override
    public String getMessage(String key, String... variables) {
        var locale = RequestContext.getLocale();
        return getMessage(locale, key, variables);
    }

    @Override
    public String getMessage(Locale locale, String key, String... variables) {
        try {
            return messageSource.getMessage(key, variables, locale);
        } catch (Exception _) {
            return key;
        }
    }

}
