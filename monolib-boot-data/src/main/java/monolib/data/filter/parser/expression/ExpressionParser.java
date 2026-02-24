package monolib.data.filter.parser.expression;

import monolib.data.filter.node.Node;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.logical.OrParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpressionParser {

    public static Node parse(ParseValue parseValue) {
        return OrParser.parse(parseValue);
    }

}
