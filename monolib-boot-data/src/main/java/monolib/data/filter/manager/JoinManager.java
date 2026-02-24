package monolib.data.filter.manager;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.HashMap;
import java.util.Map;

public class JoinManager {

    private final Root<?> root;
    private final Map<String, Join<?, ?>> joins = new HashMap<>();

    public JoinManager(Root<?> root) {
        this.root = root;
    }

    public Path<?> resolve(String fieldPath) {
        var parts = fieldPath.split("\\.");
        From<?, ?> current = root;

        for (int i = 0; i < parts.length - 1; i++) {

            String part = parts[i];

            From<?, ?> finalCurrent = current;
            current = joins.computeIfAbsent(part, _ -> finalCurrent.join(part, JoinType.LEFT));
        }

        return current.get(parts[parts.length - 1]);
    }

}
