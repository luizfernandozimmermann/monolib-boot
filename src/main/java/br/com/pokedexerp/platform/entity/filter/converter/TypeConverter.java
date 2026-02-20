package br.com.pokedexerp.platform.entity.filter.converter;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@UtilityClass
public class TypeConverter {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object convert(Object value, Class<?> targetType) {
        if (value == null) return null;

        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (targetType.equals(UUID.class)) {
            return UUID.fromString(value.toString());
        }

        if (targetType.equals(LocalDate.class)) {
            return LocalDate.parse(value.toString());
        }

        if (targetType.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(value.toString());
        }

        if (targetType.equals(LocalTime.class)) {
            return LocalTime.parse(value.toString());
        }

        if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, value.toString());
        }

        if (targetType.equals(Long.class)) {
            return Long.valueOf(value.toString());
        }

        if (targetType.equals(Integer.class)) {
            return Integer.valueOf(value.toString());
        }

        if (targetType.equals(Double.class)) {
            return Double.valueOf(value.toString());
        }

        if (targetType.equals(Boolean.class)) {
            return Boolean.valueOf(value.toString());
        }

        if (targetType.equals(String.class)) {
            return value.toString();
        }

        return value;
    }

}
