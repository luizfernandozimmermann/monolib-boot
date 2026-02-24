package monolib.data.filter.parser.impl;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import monolib.core.exception.BusinessException;
import monolib.core.exception.ErrorCode;
import monolib.core.translation.TranslationService;
import monolib.data.filter.node.Node;
import monolib.data.filter.parser.FilterParser;
import monolib.data.filter.parser.ParseValue;
import monolib.data.filter.parser.expression.ExpressionParser;
import monolib.data.utils.TranslationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilterParserImpl implements FilterParser {
    
    private static final Map<ParseValue, Node> CACHE = new ConcurrentHashMap<>();
    
    TranslationService translationService;

    @Override
    public Node parse(String filter) {
        if (StringUtils.isBlank(filter)) {
            return null;
        }
        var parseValue = ParseValue.of(filter);
        return CACHE.computeIfAbsent(parseValue, this::parse);
    }

    private Node parse(ParseValue parseValue) {
        try {
            var node = ExpressionParser.parse(parseValue);
            parseValue.skipWhiteSpace();
            if (parseValue.getPos() < parseValue.getInput().length()) {
                throw new IllegalArgumentException("Unexpected token: %s at %s".formatted(parseValue.getCurrent(), parseValue.getPos()));
            }
            return node;
        } catch (Exception e) {
            log.error("Error parsing", e);
            var errorMessage = translationService.getMessage(TranslationConstants.INVALID_FILTER);
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, errorMessage, e);
        }
    }

}
