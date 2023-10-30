package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<NumberSchema> {
    public NumberSchema positive() {
        validation = validation.and(o -> (Integer) o > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        validation = validation.and(o -> (Integer) o >= min && (Integer) o <= max);
        return this;
    }

    @Override
    protected boolean isEmpty(Object o) {
        return false;
    }

    protected boolean checkInstance(Object o) {
        return o instanceof Integer || o == null;
    }
}
