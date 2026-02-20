package br.com.pokedexerp.platform.entity.mapper;

import br.com.pokedexerp.platform.entity.model.BaseEntity;

import java.util.Collection;
import java.util.Map;

@FunctionalInterface
public interface NestedMappingFunction {
    Map<String, Object> map(BaseEntity entity, Collection<String> fields);
}
