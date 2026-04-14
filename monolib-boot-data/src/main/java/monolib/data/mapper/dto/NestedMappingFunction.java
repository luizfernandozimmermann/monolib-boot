package monolib.data.mapper.dto;

@FunctionalInterface
public interface NestedMappingFunction<S, T, R> {

    @SuppressWarnings("unchecked")
    default Object mapObject(Object source, Object target, MappingContext context) {
        return map((S) source, (T) target, context);
    }

    R map(S source, T target, MappingContext context);

}
