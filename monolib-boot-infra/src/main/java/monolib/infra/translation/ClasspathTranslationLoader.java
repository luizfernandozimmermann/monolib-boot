package monolib.infra.translation;

import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
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
            loadTranslationFiles(locales);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNKNOWN, e);
        }

        return locales;
    }

    private void loadTranslationFiles(Set<Locale> locales) throws IOException {

        var resolver = new PathMatchingResourcePatternResolver();
        var resources = resolver.getResources("classpath*:/translations/*.properties");

        Arrays.stream(resources)
                .map(Resource::getFilename)
                .filter(Objects::nonNull)
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
