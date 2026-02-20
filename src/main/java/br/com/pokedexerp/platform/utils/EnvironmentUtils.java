package br.com.pokedexerp.platform.utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class EnvironmentUtils {
    
    public static Long getLong(String variable, Long defaultValue) {
        return Optional.ofNullable(System.getenv(variable))
                .map(Long::parseLong)
                .orElse(defaultValue);
    }

    public static String getString(String variable) {
        return System.getenv(variable);
    }

    public static boolean getBoolean(String variable) {
        var value = System.getenv(variable);
        return value != null && value.equals("true");
    }
}
