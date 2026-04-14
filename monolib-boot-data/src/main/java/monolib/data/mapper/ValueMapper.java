package monolib.data.mapper;

import monolib.data.mapper.dto.FieldMappingContext;

public interface ValueMapper {
    Object map(FieldMappingContext fieldContext);
}

