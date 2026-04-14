package monolib.data.mapper;

import monolib.data.mapper.dto.FieldMappingContext;

import java.util.Collection;

public interface CollectionMapper {

    Collection<?> mapCollection(FieldMappingContext fieldContext);

}
