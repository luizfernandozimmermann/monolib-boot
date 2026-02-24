package monolib.infra.translation;

import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Component
public class ClasspathTranslationLoader implements TranslationLoader {

    @Override
    public Set<Locale> loadLocales() {
        var locales = new HashSet<Locale>();

        try {
            var resources = Thread.currentThread().getContextClassLoader().getResources("translations");

            while (resources.hasMoreElements()) {
                loadTranslationFiles(resources, locales);
            }

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN, e);
        }

        return locales;
    }

    private void loadTranslationFiles(Enumeration<URL> resources, Set<Locale> locales) throws URISyntaxException {
        Arrays.stream(Objects.requireNonNull(new File(resources.nextElement().toURI()).listFiles()))
                .map(File::getName)
                .filter(ClasspathTranslationLoader::isPropertiesFile)
                .map(ClasspathTranslationLoader::splitFileName)
                .filter(ClasspathTranslationLoader::hasTwoParts)
                .map(ClasspathTranslationLoader::splitLanguageAndCountry)
                .map(ClasspathTranslationLoader::buildLocale)
                .filter(Objects::nonNull)
                .forEach(locales::add);
    }

    private static Locale buildLocale(String[] parts) {
        if (parts.length == 1) {
            return buildLocale(parts[0], null);
        }
        if (hasTwoParts(parts)) {
            return buildLocale(parts[0], parts[1]);
        }
        return null;
    }

    private static String [] splitLanguageAndCountry(String[] parts) {
        return parts[1].split("-");
    }

    private static boolean hasTwoParts(String[] parts) {
        return parts.length == 2;
    }

    private static String [] splitFileName(String fileName) {
        return fileName.split("_");
    }

    private static boolean isPropertiesFile(String fileName) {
        return fileName.endsWith(".properties");
    }

    private static Locale buildLocale(String language, String country) {
        return new Locale.Builder()
                .setLanguage(language)
                .setRegion(country)
                .build();
    }
}
