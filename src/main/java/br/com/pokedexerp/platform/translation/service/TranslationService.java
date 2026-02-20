package br.com.pokedexerp.platform.translation.service;

import java.util.Locale;

public interface TranslationService {

    String getMessage(String key);

    String getMessage(String key, String... variables);

    String getMessage(Locale locale, String key, String... variables);

}
