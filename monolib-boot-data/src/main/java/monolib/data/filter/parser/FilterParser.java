package monolib.data.filter.parser;

import monolib.data.filter.node.Node;

public interface FilterParser {

    Node parse(String filter);

}
