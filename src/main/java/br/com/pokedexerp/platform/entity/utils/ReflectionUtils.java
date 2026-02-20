package br.com.pokedexerp.platform.entity.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

@UtilityClass
public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericClass(Class<?> clazz, int index) {
        return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationType) {
        var annotation = AnnotatedElementUtils.findMergedAnnotation(method, annotationType);
        if (annotation != null) {
            return annotation;
        }
        return AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), annotationType);
    }

    public static <T extends Annotation> T getAnnotation(AnnotatedElement target, Class<T> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(target, annotationType);
    }

}
