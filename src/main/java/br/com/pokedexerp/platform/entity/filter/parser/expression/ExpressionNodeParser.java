package br.com.pokedexerp.platform.entity.filter.parser.expression;

import br.com.pokedexerp.platform.entity.filter.node.ExpressionNode;
import br.com.pokedexerp.platform.entity.filter.node.FieldNode;
import br.com.pokedexerp.platform.entity.filter.node.FunctionNode;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.common.IdentifierParser;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class ExpressionNodeParser {

    public static ExpressionNode parse(ParseValue parseValue) {
        parseValue.skipWhiteSpace();
        var identifier = IdentifierParser.parse(parseValue);
        parseValue.skipWhiteSpace();

        if (parseValue.match("(")) {
            var args = extractArgs(parseValue);
            parseValue.expect(")");
            return new FunctionNode(identifier.toUpperCase(), args);
        }

        return new FieldNode(identifier);
    }

    private static ArrayList<ExpressionNode> extractArgs(ParseValue parseValue) {
        var args = new ArrayList<ExpressionNode>();
        if (!parseValue.peek(")")) {
            do {
                args.add(parse(parseValue));
            } while (parseValue.match(","));
        }
        return args;
    }

}
