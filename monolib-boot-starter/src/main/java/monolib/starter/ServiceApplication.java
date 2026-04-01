package monolib.starter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@ConfigurationPropertiesScan
@ComponentScan
@ServletComponentScan
@EnableJpaAuditing
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public @interface ServiceApplication {

    @AliasFor(attribute = "basePackages", annotation = EnableJpaRepositories.class)
    String[] scanRepositories() default {"monolib.*"};

    @AliasFor(attribute = "basePackages", annotation = EntityScan.class)
    String[] scanEntity() default {"monolib.*"};

    @AliasFor(attribute = "basePackages", annotation = ConfigurationPropertiesScan.class)
    String[] scanConfiguration() default {"monolib.*"};

    @AliasFor(attribute = "basePackages", annotation = ComponentScan.class)
    String[] scanComponent() default {"monolib.*"};

    @AliasFor(attribute = "basePackages", annotation = ServletComponentScan.class)
    String[] scanServletComponent() default {"monolib.*"};

}
