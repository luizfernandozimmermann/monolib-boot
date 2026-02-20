package br.com.pokedexerp.platform.custom.runner.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "resource")
public class PermissionNode {

    private String resource;

    private String description;

    private List<PermissionNode> children = new ArrayList<>();

    public PermissionNode(String resource, String description) {
        this.resource = resource;
        this.description = description;
    }

    public void addChild(PermissionNode node) {
        this.children.add(node);
    }
}
