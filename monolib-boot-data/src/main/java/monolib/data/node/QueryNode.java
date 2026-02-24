package monolib.data.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import monolib.data.filter.node.Node;
import monolib.data.order.node.OrderNode;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class QueryNode {

    private final Node filterNode;
    private final List<OrderNode> orderNode;

}
