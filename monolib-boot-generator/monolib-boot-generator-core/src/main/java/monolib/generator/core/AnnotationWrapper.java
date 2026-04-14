package monolib.generator.core;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor(staticName = "of")
public class AnnotationWrapper {

    private final AnnotationMirror mirror;

    public static AnnotationWrapper of(TypeElement element, Class<? extends Annotation> annotationClass) {
        return of(getAnnotationRecursive(element, annotationClass.getCanonicalName()));
    }

    private static AnnotationMirror getAnnotationRecursive(Element element, String targetAnnotation) {
        return getAnnotationRecursive(element, targetAnnotation, new HashSet<>(), null);
    }

    private static AnnotationMirror getAnnotationRecursive(Element element, String targetAnnotation, Set<String> visited, AnnotationMirror root) {
        for (var mirror : element.getAnnotationMirrors()) {
            var annotationElement = mirror.getAnnotationType().asElement();
            var annotationName = ((TypeElement) annotationElement)
                    .getQualifiedName()
                    .toString();

            if (!visited.add(annotationName)) {
                continue;
            }

            var currentRoot = getAnnotationMirror(targetAnnotation, visited, annotationName, getCurrentRoot(root, mirror), annotationElement);
            if (currentRoot != null) {
                return currentRoot;
            }
        }

        return null;
    }

    private static AnnotationMirror getCurrentRoot(AnnotationMirror root, AnnotationMirror mirror) {
        return (root == null) ? mirror : root;
    }

    private static AnnotationMirror getAnnotationMirror(String targetAnnotation, Set<String> visited, String annotationName, AnnotationMirror currentRoot, Element annotationElement) {
        if (annotationName.equals(targetAnnotation)) {
            return currentRoot;
        }
        return getAnnotationRecursive(annotationElement, targetAnnotation, visited, currentRoot);
    }

    public <T> T get(String name, Class<T> returnType) {
        return getInformedValue(name)
                .or(() -> getDefaultValue(name))
                .map(value -> ValueConverter.convert(value, returnType))
                .orElse(null);
    }

    private Optional<Object> getDefaultValue(String name) {
        for (var e : mirror.getAnnotationType().asElement().getEnclosedElements()) {
            if (e.getSimpleName().contentEquals(name)) {
                var defaultValue = ((ExecutableElement) e).getDefaultValue();
                if (defaultValue != null) {
                    return Optional.ofNullable(defaultValue.getValue());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Object> getInformedValue(String name) {
        for (var entry : mirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().contentEquals(name)) {
                return Optional.ofNullable(entry.getValue().getValue());
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public <T> T as(Class<T> clazz) {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (var field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            var name = field.getName();
            var type = field.getType();
            getInformedValue(name)
                    .map(value -> ValueConverter.convert(value, type))
                    .ifPresent(value -> {
                        try {
                            field.set(instance, value);
                        } catch (IllegalAccessException e) {
                            throw new IllegalArgumentException(e);
                        }
                    });
        }

        return instance;
    }

    public boolean isPresent() {
        return mirror != null;
    }
}
