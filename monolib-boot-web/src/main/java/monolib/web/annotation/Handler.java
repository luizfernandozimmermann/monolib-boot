package monolib.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Component
@RequestMapping
public @interface Handler {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};

    /**
     * @return Permissions needed
     */
    String[] permissions() default {};

    /**
     * @return Type of the request handling
     */
    ControllerType type() default ControllerType.CUSTOM;

    enum ControllerType {
        ENTITY,
        CUSTOM
    }

}
