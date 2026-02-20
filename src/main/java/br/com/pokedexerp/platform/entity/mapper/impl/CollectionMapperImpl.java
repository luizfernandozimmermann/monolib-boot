package br.com.pokedexerp.platform.entity.mapper.impl;

import br.com.pokedexerp.platform.entity.mapper.CollectionMapper;
import br.com.pokedexerp.platform.entity.mapper.NestedMapper;
import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionMapperImpl implements CollectionMapper {

    NestedMapper nestedMapper;

    @Override
    public Collection<?> mapCollection(Field sourceField, Collection<?> values, MappingConfig<?, ?, ?> config) {
        var result = new ArrayList<>();
        for (var value : values) {
            result.add(nestedMapper.mapNested(sourceField, value, config));
        }
        return result;
    }

}
