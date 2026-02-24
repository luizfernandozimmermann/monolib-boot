package monolib.data.mapper.dto;

import java.util.IdentityHashMap;
import java.util.Map;

public class MappingContext {

    private final int maxDepth;
    private int currentDepth = 0;

    private final Map<Object, Object> mapped = new IdentityHashMap<>();

    public MappingContext(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void increaseDepth() {
        currentDepth++;
    }

    public void decreaseDepth() {
        currentDepth--;
    }

    public boolean isMaxDepthReached() {
        return currentDepth >= maxDepth;
    }

    public void register(Object source, Object target) {
        mapped.put(source, target);
    }

    public boolean isAlreadyMapped(Object source) {
        return mapped.containsKey(source);
    }

    public Object getMapped(Object source) {
        return mapped.get(source);
    }
}
