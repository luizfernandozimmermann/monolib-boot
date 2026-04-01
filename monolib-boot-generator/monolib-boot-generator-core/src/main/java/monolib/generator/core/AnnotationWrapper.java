package monolib.generator.core;

import lombok.AllArgsConstructor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import java.util.Optional;

@AllArgsConstructor(staticName = "of")
public class AnnotationWrapper {

    private final AnnotationMirror mirror;

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

}
