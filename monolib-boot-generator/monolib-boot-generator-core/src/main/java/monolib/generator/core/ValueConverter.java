package monolib.generator.core;

import lombok.experimental.UtilityClass;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings({"unchecked", "rawtypes"})
public class ValueConverter {

    public <T> T convert(Object raw, Class<T> returnType) {
        if (returnType.isInstance(raw)) {
            return returnType.cast(raw);
        }
        if (raw instanceof VariableElement enumConst) {
            return convertVariableElement(returnType, enumConst);
        }
        if (raw instanceof TypeMirror typeMirror) {
            return (T) typeMirror;
        }
        if (raw instanceof List<?> list) {
            return convertList(returnType, list);
        }
        return (T) raw;
    }

    private static <T> T convertVariableElement(Class<T> returnType, VariableElement enumConst) {
        var name = enumConst.getSimpleName().toString();
        return (T) Enum.valueOf((Class<Enum>) returnType.asSubclass(Enum.class), name);
    }

    private static <T> T convertList(Class<T> returnType, List<?> list) {
        if (returnType.isArray() && returnType.getComponentType().isEnum()) {
            return convertEnumArray(returnType, list);
        }
        return convertArray(list);
    }

    private static <T> T convertArray(List<?> list) {
        var result = new ArrayList<>();
        for (var item : list) {
            if (item instanceof AnnotationValue av) {
                result.add(av.getValue());
            } else {
                result.add(item);
            }
        }
        return (T) result;
    }

    private static <T> T convertEnumArray(Class<T> returnType, List<?> list) {
        var componentType = returnType.getComponentType();
        var array = Array.newInstance(componentType, list.size());
        for (int index = 0; index < list.size(); index++) {
            var annotationValue = (AnnotationValue) list.get(index);
            var enumConst = (VariableElement) annotationValue.getValue();
            var name = enumConst.getSimpleName().toString();
            var enumValue = Enum.valueOf((Class<Enum>) componentType.asSubclass(Enum.class), name);
            Array.set(array, index, enumValue);
        }

        return (T) array;
    }

}
