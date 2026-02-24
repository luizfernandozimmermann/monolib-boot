package monolib.data.filter.node;

import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;

public interface PredicateNode extends Node {

    Predicate accept(PredicateContext predicateContext);

}
