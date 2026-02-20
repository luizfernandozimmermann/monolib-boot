package br.com.pokedexerp.platform.entity.order.parser;

import br.com.pokedexerp.platform.entity.order.node.OrderNode;

import java.util.List;

public interface OrderParser {
    List<OrderNode> parse(String orderBy);
}

