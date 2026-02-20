package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;

import java.lang.reflect.Field;
import java.util.Collection;

public interface CollectionMapper {

    Collection<?> mapCollection(Field sourceField, Collection<?> values, MappingConfig<?, ?, ?> config);

}
