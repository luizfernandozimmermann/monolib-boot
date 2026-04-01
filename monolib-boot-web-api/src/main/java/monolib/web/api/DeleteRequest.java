package monolib.web.api;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DELETE request
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiRequest(method = RequestMethod.DELETE)
public @interface DeleteRequest {

    @AliasFor(annotation = ApiRequest.class, attribute = "path")
    String[] path() default {};

    @AliasFor(annotation = ApiRequest.class, attribute = "value")
    String[] value() default {};

    @AliasFor(annotation = ApiRequest.class, attribute = "permissions")
    String[] permissions() default {};

    @AliasFor(annotation = ApiRequest.class, attribute = "authenticate")
    boolean authenticate() default true;

    @AliasFor(annotation = ApiRequest.class, attribute = "authenticateFirstSession")
    boolean authenticateFirstSession() default true;

}
