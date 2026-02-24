package monolib.data.order.parser.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationService;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.expression.ExpressionNodeParser;
import monolib.data.order.node.OrderNode;
import monolib.data.order.parser.OrderParser;
import monolib.data.utils.TranslationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderParserImpl implements OrderParser {

    private static final Map<ParseValue, List<OrderNode>> CACHE = new ConcurrentHashMap<>();

    TranslationService translationService;

    @Override
    public List<OrderNode> parse(String orderBy) {
        try {
            return parseOrderBy(orderBy);
        } catch (Exception e) {
            log.error("Error ordering", e);
            var errorMessage = translationService.getMessage(TranslationConstants.INVALID_ORDER_BY);
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, errorMessage, e);
        }
    }

    private static List<OrderNode> parseOrderBy(String orderBy) {
        if (StringUtils.isBlank(orderBy)) {
            return Collections.emptyList();
        }
        var parseValue = ParseValue.of(orderBy);
        return CACHE.computeIfAbsent(parseValue, OrderParserImpl::parse);
    }

    private static void validateFinalPosition(ParseValue parseValue) {
        if (parseValue.getPos() < parseValue.getInput().length()) {
            throw new IllegalArgumentException("Unexpected token at position " + parseValue.getPos());
        }
    }

    private static List<OrderNode> parse(ParseValue parseValue) {
        var orders = new ArrayList<OrderNode>();
        do {
            var expression = ExpressionNodeParser.parse(parseValue);
            orders.add(OrderNode.of(expression, isAsc(parseValue)));
        } while (parseValue.match(","));
        parseValue.skipWhiteSpace();
        validateFinalPosition(parseValue);
        return orders;
    }

    private static boolean isAsc(ParseValue parseValue) {
        return parseValue.matchKeyword("ASC") || !parseValue.matchKeyword("DESC");
    }

}
