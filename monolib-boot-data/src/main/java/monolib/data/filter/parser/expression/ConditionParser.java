package monolib.data.filter.parser.expression;

import monolib.data.filter.node.ConditionNode;
import monolib.data.filter.node.ExpressionNode;
import monolib.data.filter.node.ListValueNode;
import monolib.data.filter.node.Node;
import monolib.data.filter.operator.Operator;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.common.OperatorParser;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

@UtilityClass
public class ConditionParser {

    public static Node parse(ParseValue parseValue) {
        var left = ExpressionNodeParser.parse(parseValue);
        var operator = OperatorParser.parse(parseValue);

        if (operator == Operator.IS_NULL || operator == Operator.IS_NOT_NULL) {
            return new ConditionNode(left, operator, null);
        }

        if (operator == Operator.IN || operator == Operator.NOT_IN) {
            parseValue.expect("(");
            var values = extractValues(parseValue);
            parseValue.expect(")");
            return new ConditionNode(left, operator, new ListValueNode(values));
        }
        var right = ValueExpressionParser.parse(parseValue);
        return new ConditionNode(left, operator, right);
    }

    private static @NonNull ArrayList<ExpressionNode> extractValues(ParseValue parseValue) {
        var values = new ArrayList<ExpressionNode>();
        do {
            values.add(ValueExpressionParser.parse(parseValue));
        } while (parseValue.match(","));
        return values;
    }

}
