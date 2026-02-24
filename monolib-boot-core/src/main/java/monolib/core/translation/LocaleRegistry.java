package monolib.core.translation;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@UtilityClass
public class LocaleRegistry {

    public static final Locale DEFAULT_LOCALE = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

    private static final Map<String, Locale> LOCALES = new HashMap<>();

    static {
        LOCALES.put("pt-BR", DEFAULT_LOCALE);
    }

    public static void register(Locale locale) {
        LOCALES.putIfAbsent("%s-%s".formatted(locale.getLanguage(), locale.getCountry()), locale);
    }

    public static Locale getLocale(String lang) {
        return LOCALES.getOrDefault(lang, DEFAULT_LOCALE);
    }

}
