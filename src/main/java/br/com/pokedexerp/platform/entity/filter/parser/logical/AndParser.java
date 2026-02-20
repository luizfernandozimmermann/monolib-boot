package br.com.pokedexerp.platform.entity.filter.parser.logical;

import br.com.pokedexerp.platform.entity.filter.node.LogicalNode;
import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.operator.LogicalOperator;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.common.PrimaryParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AndParser {

    public static Node parse(ParseValue parseValue) {
        var left = PrimaryParser.parse(parseValue);
        while (parseValue.matchKeyword("AND")) {
            var right = PrimaryParser.parse(parseValue);
            left = new LogicalNode(left, right, LogicalOperator.AND);
        }
        return left;
    }

}
