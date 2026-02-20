package br.com.pokedexerp.platform.entity.mapper.dto;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ProjectionNode {
    private boolean wildcard;
    private final Set<String> children = new HashSet<>();

    public static ProjectionNode wildcard() {
        var node = new ProjectionNode();
        node.wildcard = true;
        return node;
    }

    public void addChild(String child) {
        children.add(child);
    }
}
