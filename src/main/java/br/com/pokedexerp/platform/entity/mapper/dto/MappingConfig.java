package br.com.pokedexerp.platform.entity.mapper.dto;

import br.com.pokedexerp.platform.entity.mapper.NestedMappingFunction;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Getter
@Builder
public class MappingConfig<E extends BaseEntity, S, T> {

    private Class<E> entityClass;
    private S source;
    private T target;
    private ProjectionEngine projection;
    @Setter
    private boolean includeNonUpdatable;
    private MappingContext context;
    private NestedMappingFunction nestedMappingFunction;

    public boolean shouldMap(String field) {
        return projection == null || projection.shouldMap(field);
    }

    public Collection<String> childProjection(String field) {
        return projection == null ? null : projection.childProjection(field);
    }

    public boolean isTargetMap() {
        return target instanceof Map<?,?>;
    }

    @SuppressWarnings("unchecked")
    public void setMapField(String fieldName, Object object) {
        if (isTargetMap()) {
            ((Map<String, Object>) target).put(fieldName, object);
        }
    }
}
