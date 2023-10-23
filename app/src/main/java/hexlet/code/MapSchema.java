package hexlet.code;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private Integer sizeof;
    private Map<String, BaseSchema> shape;

    public MapSchema sizeof(int size) {
        sizeof = size;
        needCheckMethods.add("checkSizeOf");
        return this;
    }
    public void shape(Map<String, BaseSchema> schemas) {
        this.shape = schemas;
        needCheckMethods.add("checkShapeSchemas");
    }

    private boolean checkSizeOf(Object tested) {
        return sizeof == null || tested == null || ((Map) tested).size() == sizeof;
    }
    @SuppressWarnings("unchecked")
    private boolean checkShapeSchemas(Object tested) {
        Map<String, Object> schemas = (Map<String, Object>) tested;
        for (String key : this.shape.keySet()) {
            if (!this.shape.get(key).isValid(schemas.get(key))) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkInstance(Object o) {
        return o instanceof Map || o == null;
    }
}
