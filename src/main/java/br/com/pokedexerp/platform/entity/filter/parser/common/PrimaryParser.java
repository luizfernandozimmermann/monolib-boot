package br.com.pokedexerp.platform.entity.filter.parser.common;

import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.expression.ConditionParser;
import br.com.pokedexerp.platform.entity.filter.parser.expression.ExpressionParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PrimaryParser {

    public static Node parse(ParseValue parseValue) {
        parseValue.skipWhiteSpace();
        if (parseValue.match("(")) {
            var node = ExpressionParser.parse(parseValue);
            parseValue.expect(")");
            return node;
        }
        return ConditionParser.parse(parseValue);
    }

}
