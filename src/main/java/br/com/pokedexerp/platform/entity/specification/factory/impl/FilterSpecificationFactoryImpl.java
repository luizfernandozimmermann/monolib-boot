package br.com.pokedexerp.platform.entity.specification.factory.impl;

import br.com.pokedexerp.platform.entity.node.QueryNode;
import br.com.pokedexerp.platform.entity.filter.parser.FilterParser;
import br.com.pokedexerp.platform.entity.order.parser.OrderParser;
import br.com.pokedexerp.platform.entity.specification.FilterSpecification;
import br.com.pokedexerp.platform.entity.specification.factory.FilterSpecificationFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilterSpecificationFactoryImpl implements FilterSpecificationFactory {

    FilterParser filterParser;

    OrderParser orderParser;

    @Override
    public <T> FilterSpecification<T> create(String filter, String orderBy) {
        var queryNode = QueryNode.of(filterParser.parse(filter), orderParser.parse(orderBy));
        return FilterSpecification.<T>of(queryNode);
    }

}
