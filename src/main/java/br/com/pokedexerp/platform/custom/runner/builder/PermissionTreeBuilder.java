package br.com.pokedexerp.platform.custom.runner.builder;

import br.com.pokedexerp.platform.custom.runner.dto.PermissionNode;
import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class PermissionTreeBuilder {

    public static final String PARENT_DESCRIPTION = "Permissions for %s actions";
    public static final String ACTION_DESCRIPTION = "Permission to %s %s";

    public static List<PermissionNode> build(List<String> permissions) {
        var nodes = new LinkedHashMap<String, PermissionNode>();
        for (var permission : permissions) {
            processPermission(permission, nodes);
        }
        return findRootNodes(nodes);
    }

    private static void processPermission(String fullName, Map<String, PermissionNode> nodes) {
        var parts = parsePermission(fullName);
        var parent = buildHierarchy(parts.hierarchy(), nodes);
        if (parts.hasAction()) {
            attachActionNode(parts, parent, nodes);
        }
    }

    private static PermissionParts parsePermission(String fullName) {
        var actionSplit = fullName.split(":");
        var hierarchy = actionSplit[0];
        var action = actionSplit.length > 1 ? actionSplit[1] : null;
        return new PermissionParts(hierarchy, action);
    }

    private static PermissionNode buildHierarchy(String hierarchy, Map<String, PermissionNode> nodes) {
        var levels = hierarchy.split("\\.");
        var currentPath = new StringBuilder();
        PermissionNode parent = null;

        for (int i = 0; i < levels.length; i++) {
            appendLevel(currentPath, levels[i], i);
            var finalI = i;
            var current = nodes.computeIfAbsent(currentPath.toString(),
                    path -> new PermissionNode(path, generateParentDescription(levels[finalI])));
            linkParent(parent, current);
            parent = current;
        }
        return parent;
    }

    private static void appendLevel(StringBuilder path, String level, int index) {
        if (index > 0) {
            path.append(".");
        }
        path.append(level);
    }

    private static void linkParent(PermissionNode parent, PermissionNode child) {
        if (parent != null && !parent.getChildren().contains(child)) {
            parent.addChild(child);
        }
    }

    private static void attachActionNode(PermissionParts parts, PermissionNode parent, Map<String, PermissionNode> nodes) {
        var actionKey = parts.hierarchy() + ":" + parts.action();
        if (nodes.containsKey(actionKey)) {
            return;
        }
        var resource = extractResourceName(parts.hierarchy());
        var actionNode = new PermissionNode(actionKey, generateActionDescription(parts.action(), resource));
        nodes.put(actionKey, actionNode);
        parent.addChild(actionNode);
    }

    private static String extractResourceName(String hierarchy) {
        var levels = hierarchy.split("\\.");
        return levels[levels.length - 1];
    }

    private static List<PermissionNode> findRootNodes(Map<String, PermissionNode> nodes) {
        return nodes.values().stream()
                .filter(node -> isRoot(node, nodes))
                .toList();
    }

    private static boolean isRoot(PermissionNode node, Map<String, PermissionNode> nodes) {
        return nodes.values().stream()
                .noneMatch(parent -> parent.getChildren().contains(node));
    }

    private static String generateParentDescription(String name) {
        return PARENT_DESCRIPTION.formatted(humanize(name));
    }

    private static String generateActionDescription(String action, String resource) {
        return ACTION_DESCRIPTION.formatted(action, humanize(resource));
    }

    private static String humanize(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    private record PermissionParts(String hierarchy, String action) {
        boolean hasAction() {
            return action != null && !action.isBlank();
        }
    }

}
