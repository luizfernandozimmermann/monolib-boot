package monolib.infra.config;

import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TranslationConfig {

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String TRANSLATIONS_FOLDER = "translations/messages";

    @Bean
    @Primary
    @SneakyThrows
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        var basenames = getBasenames();
        ms.setBasenames(basenames.toArray(String[]::new));
        ms.setDefaultEncoding(DEFAULT_ENCODING);
        ms.setFallbackToSystemLocale(false);
        return ms;
    }

    private static List<String> getBasenames() throws IOException {
        var basenames = new ArrayList<String>();
        basenames.add(TRANSLATIONS_FOLDER);
        var resolver = new PathMatchingResourcePatternResolver();
        var resources = resolver.getResources("classpath*:/translations/*_messages*.properties");

        for (var resource : resources) {
            var uri = resource.getURI().toString();
            var base = uri
                    .substring(uri.indexOf("/translations/"), uri.indexOf("_messages") + 9);
            basenames.add("classpath:" + base);
        }
        return basenames;
    }

}
