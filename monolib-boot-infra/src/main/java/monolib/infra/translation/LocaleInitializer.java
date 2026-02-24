package monolib.infra.translation;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.translation.LocaleRegistry;
import monolib.core.translation.TranslationLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocaleInitializer {

    List<TranslationLoader> loaders;

    @PostConstruct
    public void init() {
        loaders.stream().map(TranslationLoader::loadLocales)
                .flatMap(Set::stream)
                .forEach(LocaleRegistry::register);
    }

}
