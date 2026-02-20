package br.com.pokedexerp.platform.custom.config;

import br.com.pokedexerp.platform.messaging.context.ServiceContext;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareConfiguration implements AuditorAware<String> {

    @Override
    @NullMarked
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(ServiceContext.getEmail());
    }

}
