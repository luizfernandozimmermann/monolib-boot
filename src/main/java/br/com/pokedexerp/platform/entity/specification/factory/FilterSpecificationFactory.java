package br.com.pokedexerp.platform.entity.specification.factory;

import br.com.pokedexerp.platform.entity.specification.FilterSpecification;

public interface FilterSpecificationFactory {

    <T> FilterSpecification<T> create(String filter, String orderBy);

}
