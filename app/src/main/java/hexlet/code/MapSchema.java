package hexlet.code;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private Integer sizeof;

    public MapSchema sizeof(int size) {
        sizeof = size;
        return this;
    }

    public boolean isValid(Object o) {
        if (!(o instanceof Map) && o != null) {
            return false;
        }
        if (o == null && required) {
            return false;
        }
        return sizeof == null || (o != null && ((Map) o).size() == sizeof);
    }
}
