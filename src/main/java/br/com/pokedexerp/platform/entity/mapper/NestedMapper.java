package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;

import java.lang.reflect.Field;

public interface NestedMapper {
    Object mapNested(Field sourceField, Object value, MappingConfig<?, ?, ?> config);
}
