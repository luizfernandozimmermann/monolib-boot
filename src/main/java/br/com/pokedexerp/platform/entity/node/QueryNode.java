package br.com.pokedexerp.platform.entity.node;

import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.order.node.OrderNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class QueryNode {

    private final Node filterNode;
    private final List<OrderNode> orderNode;

}
