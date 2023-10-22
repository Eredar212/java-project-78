package hexlet.code;

public class NumberSchema extends BaseSchema {
    //private boolean required = false;
    private boolean positive = false;
    private Integer minRange;
    private Integer maxRange;
    private boolean checkRange = false;

    /*public NumberSchema required() {
        this.required = true;
        return this;
    }*/
    public NumberSchema positive() {
        this.positive = true;
        return this;
    }
    public NumberSchema range(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
        checkRange = true;
        return this;
    }
    @Override
    public boolean isValid(Object tested) {
        if (!(tested instanceof Integer) && tested != null) {
            return false;
        }
        if (tested == null && required) {
            return false;
        }
        if (positive && tested != null && (Integer) tested <= 0) {
            return false;
        }
        return !checkRange || (tested != null && (Integer) tested >= minRange && (Integer) tested <= maxRange);
    }
}
