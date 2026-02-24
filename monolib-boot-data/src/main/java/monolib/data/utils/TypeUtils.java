package monolib.data.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class TypeUtils {

    public static boolean isSimpleType(Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || Number.class.isAssignableFrom(type)
                || type.equals(Boolean.class)
                || type.isEnum()
                || type.getPackageName().startsWith("java.time");
    }

    public static boolean isCollection(Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

}
