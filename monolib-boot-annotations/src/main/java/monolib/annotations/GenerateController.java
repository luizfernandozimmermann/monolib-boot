package monolib.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@GenerateCRUDService
@GenerateDto
public @interface GenerateController {

    @AliasFor(annotation = GenerateCRUDService.class, attribute = "noRepositoryBean")
    boolean noRepositoryBean() default true;

    String group() default "entity";

    Operation[] operations() default {
        Operation.CREATE,
        Operation.READ,
        Operation.UPDATE,
        Operation.DELETE
    };

    enum Operation {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

}
