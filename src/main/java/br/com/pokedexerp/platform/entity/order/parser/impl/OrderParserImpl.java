package br.com.pokedexerp.platform.entity.order.parser.impl;

import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.expression.ExpressionNodeParser;
import br.com.pokedexerp.platform.entity.order.node.OrderNode;
import br.com.pokedexerp.platform.entity.order.parser.OrderParser;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new ServiceException(HttpStatus.BAD_REQUEST, errorMessage, e);
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

