package monolib.data.mapper.factory.impl;

import monolib.data.mapper.dto.ProjectionEngine;
import monolib.data.mapper.dto.ProjectionNode;
import monolib.data.mapper.factory.ProjectionEngineFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProjectionEngineFactoryImpl implements ProjectionEngineFactory {

    @Override
    public ProjectionEngine create(Collection<String> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return ProjectionEngine.empty();
        }
        var map = mapFields(fields);
        return ProjectionEngine.of(map);
    }

    private static Map<String, ProjectionNode> mapFields(Collection<String> fields) {
        var map = new HashMap<String, ProjectionNode>();
        fields.forEach(field -> addFieldToMap(field, map));
        map.computeIfAbsent("id", _ -> new ProjectionNode());
        return map;
    }

    private static void addFieldToMap(String field, HashMap<String, ProjectionNode> map) {
        if (field.endsWith(".*")) {
            var root = field.substring(0, field.length() - 2);
            map.put(root, ProjectionNode.wildcard());
            return;
        }
        addChildField(field, map);
    }

    private static void addChildField(String field, HashMap<String, ProjectionNode> map) {
        var parts = field.split("\\.", 2);
        var root = parts[0];
        var node = map.computeIfAbsent(root, _ -> new ProjectionNode());
        if (parts.length > 1) {
            node.addChild(parts[1]);
        }
    }

}
