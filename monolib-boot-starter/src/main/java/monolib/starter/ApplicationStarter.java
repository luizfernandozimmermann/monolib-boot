package monolib.starter;

import lombok.experimental.UtilityClass;
import org.springframework.boot.SpringApplication;

@UtilityClass
public class ApplicationStarter {
    public static void start(Class<?> primarySource, String[] args) {
        SpringApplication.run(primarySource, args);
    }
}
