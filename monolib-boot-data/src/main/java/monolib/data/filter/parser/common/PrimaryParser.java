package monolib.data.filter.parser.common;

import monolib.data.filter.node.Node;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.expression.ConditionParser;
import monolib.data.filter.parser.expression.ExpressionParser;
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
