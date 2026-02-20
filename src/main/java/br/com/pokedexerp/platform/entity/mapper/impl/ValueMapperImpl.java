package br.com.pokedexerp.platform.entity.mapper.impl;

import br.com.pokedexerp.platform.entity.mapper.CollectionMapper;
import br.com.pokedexerp.platform.entity.mapper.NestedMapper;
import br.com.pokedexerp.platform.entity.mapper.ValueMapper;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;
import br.com.pokedexerp.platform.entity.model.BaseEntity;
import br.com.pokedexerp.platform.entity.utils.TypeUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValueMapperImpl implements ValueMapper {

    NestedMapper nestedMapper;

    CollectionMapper collectionMapper;

    @Override
    public Object map(Field sourceField, Object value, MappingConfig<?, ?, ?> config) {
        if (value == null) {
            return null;
        }

        if (config.getContext().isMaxDepthReached()) {
            return null;
        }

        if (TypeUtils.isSimpleType(value.getClass())) {
            return value;
        }

        if (TypeUtils.isCollection(value.getClass())) {
            return collectionMapper.mapCollection(sourceField, (Collection<?>) value, config);
        }

        if (BaseEntity.class.isAssignableFrom(value.getClass())) {
            return nestedMapper.mapNested(sourceField, value, config);
        }

        return value;
    }

}

