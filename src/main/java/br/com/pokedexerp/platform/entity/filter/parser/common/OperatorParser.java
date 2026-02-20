package br.com.pokedexerp.platform.entity.filter.parser.common;

import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OperatorParser {

    public static Operator parse(ParseValue parseValue) {
        return Operator.orderedValues().stream()
                .filter(operator -> matches(parseValue, operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operator at position: " + parseValue.getPos()));
    }

    private static boolean matches(ParseValue parseValue, Operator operator) {
        int startPos = parseValue.getPos();

        for (var token : operator.getTokens()) {
            boolean matched = matches(parseValue, token);

            if (!matched) {
                parseValue.setPos(startPos);
                return false;
            }
        }

        return true;
    }

    private static boolean matches(ParseValue parseValue, String token) {
        return Character.isLetter(token.charAt(0)) ? parseValue.match(token): parseValue.matchKeyword(token);
    }

}
