package monolib.data.order.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import monolib.data.filter.node.ExpressionNode;

@Getter
@AllArgsConstructor(staticName = "of")
public class OrderNode {

    private final ExpressionNode expression;
    private final boolean ascending;

}
