package br.com.pokedexerp.platform.messaging.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Component
public @interface Handler {
    @AliasFor(annotation = RestController.class)
    String value() default "";

    /**
     * @return Permissions needed
     */
    String[] permissions() default {};

}
