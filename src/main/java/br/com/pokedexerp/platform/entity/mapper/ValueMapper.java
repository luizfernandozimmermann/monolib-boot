package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;

import java.lang.reflect.Field;

public interface ValueMapper {
    Object map(Field sourceField, Object value, MappingConfig<?, ?, ?> config);
}

