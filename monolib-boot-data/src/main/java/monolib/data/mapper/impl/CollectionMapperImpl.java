package monolib.data.mapper.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import monolib.data.mapper.CollectionMapper;
import monolib.data.mapper.NestedMapper;
import monolib.data.mapper.dto.FieldMappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionMapperImpl implements CollectionMapper {

    NestedMapper nestedMapper;

    @Override
    public Collection<?> mapCollection(FieldMappingContext fieldContext) {
        var result = new ArrayList<>();
        for (var value : (Collection<?>) fieldContext.getSourceValue()) {
            result.add(nestedMapper.mapNested(fieldContext, value));
        }
        return result;
    }

}
