package br.com.pokedexerp.platform.entity.filter.parser.logical;

import br.com.pokedexerp.platform.entity.filter.node.LogicalNode;
import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.operator.LogicalOperator;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrParser {

    public static Node parse(ParseValue parseValue) {
        var left = AndParser.parse(parseValue);
        while (parseValue.matchKeyword("OR")) {
            var right = AndParser.parse(parseValue);
            left = new LogicalNode(left, right, LogicalOperator.OR);
        }
        return left;
    }

}
