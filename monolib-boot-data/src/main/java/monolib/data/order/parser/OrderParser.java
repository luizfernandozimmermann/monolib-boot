package monolib.data.order.parser;

import monolib.data.order.node.OrderNode;

import java.util.List;

public interface OrderParser {
    List<OrderNode> parse(String orderBy);
}

