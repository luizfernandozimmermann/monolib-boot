package br.com.pokedexerp.platform.custom.runner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.flywaydb.core.Flyway;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Order(1)
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlatformMigrationRunner implements ApplicationRunner {

    DataSource dataSource;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/platform/migration")
                .table("flyway_schema_history_platform")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
