package br.com.pokedexerp.platform.entity.mapper.factory;

import br.com.pokedexerp.platform.entity.mapper.dto.ProjectionEngine;

import java.util.Collection;

public interface ProjectionEngineFactory {
    ProjectionEngine create(Collection<String> fields);
}
