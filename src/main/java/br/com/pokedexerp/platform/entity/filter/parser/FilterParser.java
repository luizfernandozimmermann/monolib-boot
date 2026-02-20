package br.com.pokedexerp.platform.entity.filter.parser;

import br.com.pokedexerp.platform.entity.filter.node.Node;

public interface FilterParser {

    Node parse(String filter);

}
