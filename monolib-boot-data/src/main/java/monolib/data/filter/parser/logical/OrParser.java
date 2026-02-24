package monolib.data.filter.parser.logical;

import monolib.data.filter.node.LogicalNode;
import monolib.data.filter.node.Node;
import monolib.data.filter.operator.LogicalOperator;
import monolib.data.filter.parser.ParseValue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrParser {

    public static Node parse(ParseValue parseValue) {
        var left = AndParser.parse(parseValue);
        while (parseValue.matchKeyword("OR")) {
            var right = AndParser.parse(parseValue);
            left = new LogicalNode(left, right, LogicalOperator.OR);
        }
        return left;
    }

}
