package monolib.data.filter.parser.expression;

import monolib.data.filter.node.ExpressionNode;
import monolib.data.filter.node.ValueNode;
import monolib.data.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueExpressionParser {

    public static ExpressionNode parse(ParseValue parseValue) {
        var token = ValueTokenExpressionParser.parse(parseValue);

        if (token.startsWith("'") && token.endsWith("'")) {
            var value = token.substring(1, token.length() - 1);
            return new ValueNode(value);
        }

        if (token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false")) {
            return new ValueNode(Boolean.parseBoolean(token));
        }

        if (isNumeric(token)) {
            if (token.contains(".")) {
                return new ValueNode(Double.parseDouble(token));
            }
            return new ValueNode(Long.parseLong(token));
        }

        throw new IllegalArgumentException("Invalid literal. Strings must be enclosed in single quotes.");
    }

    private static boolean isNumeric(String value) {
        return value.matches("-?\\d+(\\.\\d+)?");
    }

}
