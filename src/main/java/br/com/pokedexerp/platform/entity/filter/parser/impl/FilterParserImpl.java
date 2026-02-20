package br.com.pokedexerp.platform.entity.filter.parser.impl;

import br.com.pokedexerp.platform.entity.filter.node.Node;
import br.com.pokedexerp.platform.entity.filter.parser.FilterParser;
import br.com.pokedexerp.platform.entity.filter.parser.ParseValue;
import br.com.pokedexerp.platform.entity.filter.parser.expression.ExpressionParser;
import br.com.pokedexerp.platform.messaging.exception.ServiceException;
import br.com.pokedexerp.platform.translation.service.TranslationService;
import br.com.pokedexerp.platform.translation.utils.TranslationConstants;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new ServiceException(HttpStatus.BAD_REQUEST, errorMessage, e);
        }
    }

}
