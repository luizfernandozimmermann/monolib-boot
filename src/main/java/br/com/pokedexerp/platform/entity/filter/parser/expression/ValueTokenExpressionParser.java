package br.com.pokedexerp.platform.entity.filter.parser.expression;

import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueTokenExpressionParser {

    public static String parse(ParseValue parseValue) {
        parseValue.skipWhiteSpace();
        if (parseValue.getInput().charAt(parseValue.getPos()) == '\'') {
            int start = parseValue.increment();
            while (parseValue.getPos() < parseValue.getInput().length() && parseValue.getInput().charAt(parseValue.getPos()) != '\'') parseValue.increment();
            parseValue.increment();
            return parseValue.getInput().substring(start, parseValue.getPos());
        }

        int start = parseValue.getPos();
        while (parseValue.getPos() < parseValue.getInput().length() &&
                !Character.isWhitespace(parseValue.getInput().charAt(parseValue.getPos())) &&
                parseValue.getInput().charAt(parseValue.getPos()) != ')') {
            parseValue.increment();
        }

        return parseValue.getInput().substring(start, parseValue.getPos());
    }

}
