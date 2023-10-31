package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<MapSchema> {
    //при установке нескольких разных значений, результат всегда будет false
    public MapSchema sizeof(Integer size) {
        checkList.add(o -> size == null || ((Map<?, ?>) o).size() == size);
        return this;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MapSchema shape(Map<String, BaseSchema> shape) {
        checkList.add(o -> {
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

    @Override
    protected boolean isNullOrEmpty(Object o) {
        return o == null;
    }

    protected boolean checkInstance(Object o) {
        return o instanceof Map;
    }
}
