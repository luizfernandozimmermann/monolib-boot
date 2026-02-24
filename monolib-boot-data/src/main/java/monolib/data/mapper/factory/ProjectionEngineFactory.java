package monolib.data.mapper.factory;

import monolib.data.mapper.dto.ProjectionEngine;

import java.util.Collection;

public interface ProjectionEngineFactory {
    ProjectionEngine create(Collection<String> fields);
}
