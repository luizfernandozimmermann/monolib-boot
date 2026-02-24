package monolib.core.translation;

import java.util.Locale;
import java.util.Set;

public interface TranslationLoader {
    Set<Locale> loadLocales();
}
