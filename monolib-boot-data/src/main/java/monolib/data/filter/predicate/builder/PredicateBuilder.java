package monolib.data.filter.predicate.builder;

import monolib.data.filter.node.ConditionNode;
import monolib.data.filter.node.LogicalNode;
import monolib.data.filter.node.Node;
import monolib.data.filter.node.PredicateNode;
import monolib.data.filter.predicate.dto.PredicateContext;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PredicateBuilder {

    public static Predicate build(PredicateContext predicateContext, Node node) {
        if (Objects.isNull(node)) {
            return predicateContext.getCriteriaBuilder().conjunction();
        }
        if (node instanceof PredicateNode predicateNode) {
            return predicateNode.accept(predicateContext);
        }
        throw new IllegalArgumentException("Root node must be a predicate");
    }

    public static Predicate build(PredicateContext predicateContext, ConditionNode node) {
        return ConditionPredicateBuilder.build(predicateContext, node);
    }

    public static Predicate build(PredicateContext predicateContext, LogicalNode node) {
        return LogicalPredicateBuilder.build(predicateContext, node);
    }

}
