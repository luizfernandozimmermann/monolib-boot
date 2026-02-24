package monolib.data.config;

import monolib.data.properties.DataProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataProperties.class)
public class MonolibDataAutoConfiguration {

    @Bean
    public DataSource dataSource(DataProperties properties) {
        return DataSourceBuilder.create()
                .driverClassName(properties.getDriver())
                .url(properties.getUrl())
                .username(properties.getUser())
                .password(properties.getPassword())
                .build();
    }
}
