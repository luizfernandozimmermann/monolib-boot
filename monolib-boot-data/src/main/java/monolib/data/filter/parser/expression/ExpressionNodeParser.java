package monolib.data.filter.parser.expression;

import lombok.experimental.UtilityClass;
import monolib.data.filter.node.ExpressionNode;
import monolib.data.filter.node.FieldNode;
import monolib.data.filter.node.FunctionNode;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.common.IdentifierParser;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ExpressionNodeParser {

    public static ExpressionNode parse(ParseValue parseValue) {
        var identifier = IdentifierParser.parse(parseValue);
        parseValue.skipWhiteSpace();

        if (parseValue.peek("(")) {
            parseValue.expect("(");
            var args = extractArgs(parseValue);
            parseValue.expect(")");
            return new FunctionNode(identifier.toUpperCase(), args);
        }

        return new FieldNode(identifier);
    }

    private static List<ExpressionNode> extractArgs(ParseValue parseValue) {
        var args = new ArrayList<ExpressionNode>();
        if (!parseValue.peek(")")) {
            do {
                parseValue.skipWhiteSpace();
                if (isLiteral(parseValue)) {
                    args.add(ValueExpressionParser.parse(parseValue));
                } else {
                    args.add(parse(parseValue));
                }
                parseValue.skipWhiteSpace();
            } while (parseValue.match(","));
        }
        return args;
    }

    private static boolean isLiteral(ParseValue parseValue) {
        var current = parseValue.getCurrentChar();
        return current == '\'' || Character.isDigit(current) || current == '-';
    }

}
