package monolib.web.api;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Handler(type = Handler.ControllerType.ENTITY)
public @interface EntityHandler {

    @AliasFor(annotation = Handler.class, attribute = "path")
    String[] path() default {};

    @AliasFor(annotation = Handler.class, attribute = "permissions")
    String[] permissions() default {};

}
