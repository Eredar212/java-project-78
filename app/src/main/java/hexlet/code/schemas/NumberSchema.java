package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<NumberSchema> {
    private Integer minRange;
    private Integer maxRange;


    public NumberSchema positive() {
        needCheckMethods.add("checkPositive");
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
        needCheckMethods.add("checkRange");
        return this;
    }

    private static boolean checkPositive(Object tested) {
        return tested == null || (Integer) tested > 0;
    }
    private boolean checkRange(Object tested) {
        return tested == null || ((Integer) tested >= minRange && (Integer) tested <= maxRange);
    }
    protected boolean checkInstance(Object o) {
        return o instanceof Integer || o == null;
    }
}
