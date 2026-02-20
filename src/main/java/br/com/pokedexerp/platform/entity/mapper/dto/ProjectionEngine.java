package br.com.pokedexerp.platform.entity.mapper.dto;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor(staticName = "of")
public class ProjectionEngine {

    private final Map<String, ProjectionNode> nodes;

    public static ProjectionEngine empty() {
        return ProjectionEngine.of(null);
    }

    public boolean shouldMap(String field) {
        return Objects.isNull(nodes) || nodes.containsKey(field);
    }

    public Collection<String> childProjection(String field) {
        if (Objects.isNull(nodes)) {
            return Collections.emptyList();
        }
        var node = nodes.get(field);
        if (Objects.isNull(node) || node.isWildcard()) {
            return Collections.emptyList();
        }
        var children = new HashSet<>(node.getChildren());
        children.add("id");
        return children;
    }

}
