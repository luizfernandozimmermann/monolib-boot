package br.com.pokedexerp.platform.messaging.utils;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.Map;

@UtilityClass
public class LocaleUtils {

    public static final Locale DEFAULT_LOCALE = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

    public static final Map<String, Locale> LOCALES = Map.of("pt-BR", DEFAULT_LOCALE);

    public static Locale getLocale(String lang) {
        return LOCALES.getOrDefault(lang, DEFAULT_LOCALE);
    }

}
