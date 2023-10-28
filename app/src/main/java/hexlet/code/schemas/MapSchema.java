package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<MapSchema> {
    public MapSchema sizeof(Integer size) {
        validation = validation.and(o -> size == null || ((Map<?, ?>) o).size() == size);
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MapSchema shape(Map<String, BaseSchema> shape) {
        validation = validation.and(o -> {
            Map<String, Object> schemas = (Map<String, Object>) o;
            for (String key : shape.keySet()) {
                if (!schemas.containsKey(key) || !shape.get(key).isValid(schemas.get(key))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }

    protected boolean checkInstance(Object o) {
        return o instanceof Map || o == null;
    }
}
