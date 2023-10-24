package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<MapSchema> {
    private Integer sizeof;
    @SuppressWarnings("rawtypes")
    private Map<String, BaseSchema> shape;

    public MapSchema sizeof(int size) {
        sizeof = size;
        needCheckMethods.add("checkSizeOf");
        return this;
    }
    @SuppressWarnings("rawtypes")
    public void shape(Map<String, BaseSchema> schemas) {
        this.shape = schemas;
        needCheckMethods.add("checkShapeSchemas");
    }

    private boolean checkSizeOf(Object tested) {
        return tested == null || sizeof == null || ((Map<?, ?>) tested).size() == sizeof;
    }
    @SuppressWarnings("unchecked")
    private boolean checkShapeSchemas(Object tested) {
        if (tested == null) {
            return true;
        }
        Map<String, Object> schemas = (Map<String, Object>) tested;
        for (String key : this.shape.keySet()) {
            if (!schemas.containsKey(key)) {
                return false;
            }
            if (!this.shape.get(key).isValid(schemas.get(key))) {
                return false;
            }
        }
        return true;
    }
    protected boolean checkInstance(Object o) {
        return o instanceof Map || o == null;
    }
}
