package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.mapper.dto.MappingConfig;

public interface FieldMapper {
    void mapFields(MappingConfig<?, ?, ?> config);
}
