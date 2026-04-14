package monolib.data.mapper;

import monolib.data.mapper.dto.FieldMappingContext;

public interface NestedMapper {
    Object mapNested(FieldMappingContext fieldContext, Object value);
}
