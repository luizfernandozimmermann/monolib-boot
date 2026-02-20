package br.com.pokedexerp.platform.entity.order.node;

import br.com.pokedexerp.platform.entity.filter.node.ExpressionNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class OrderNode {

    private final ExpressionNode expression;
    private final boolean ascending;

}

