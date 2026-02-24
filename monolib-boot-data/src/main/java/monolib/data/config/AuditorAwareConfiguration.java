package monolib.data.config;

import monolib.core.context.ContextHolder;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareConfiguration implements AuditorAware<String> {

    @Override
    @NullMarked
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(ContextHolder.get().getEmail());
    }

}
