package br.com.pokedexerp.platform.entity.filter.parser.expression;

import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.logical.OrParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpressionParser {

    public static Node parse(ParseValue parseValue) {
        return OrParser.parse(parseValue);
    }

}
