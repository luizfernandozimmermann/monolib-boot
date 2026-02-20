package br.com.pokedexerp.platform.entity.filter.parser.expression;

import br.com.pokedexerp.platform.entity.filter.node.ConditionNode;
import br.com.pokedexerp.platform.entity.filter.node.ExpressionNode;
import br.com.pokedexerp.platform.entity.filter.node.ListValueNode;
import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.operator.Operator;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.common.OperatorParser;
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
