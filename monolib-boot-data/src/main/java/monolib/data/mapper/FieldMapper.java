package monolib.data.mapper;

import monolib.data.mapper.dto.MappingConfig;

public interface FieldMapper {
    void mapFields(MappingConfig<?, ?, ?> config);
}
