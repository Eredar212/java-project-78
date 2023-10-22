package hexlet.code;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private Integer sizeof;
    Map<String, BaseSchema> shape;

    public MapSchema sizeof(int size) {
        sizeof = size;
        return this;
    }

    public void shape(Map<String, BaseSchema> schemas) {
        this.shape = schemas;
    }
    @SuppressWarnings("unchecked")
    public boolean isValid(Object o) {
        if (!(o instanceof Map) && o != null) {
            return false;
        }
        if (o == null && required) {
            return false;
        }
        if (o != null && shape != null) {
            Map<String, Object> schemas = (Map<String, Object>) o;
            for (String key : this.shape.keySet()) {
                if (!this.shape.get(key).isValid(schemas.get(key))) {
                    return false;
                }
            }
        }
        return sizeof == null || (o != null && ((Map) o).size() == sizeof);
    }
}
