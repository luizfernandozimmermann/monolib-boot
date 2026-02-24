package monolib.infra.runner.initializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.core.runner.ApplicationStartedRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationStartedRunnerInitializer {

    List<ApplicationStartedRunner> runners;

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        runners.forEach(ApplicationStartedRunner::run);
    }

}
