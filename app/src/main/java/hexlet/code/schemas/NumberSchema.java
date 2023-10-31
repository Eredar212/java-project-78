package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<NumberSchema> {
    public NumberSchema positive() {
        checkList.add(o -> (Integer) o > 0);
        return this;
    }

    //при установке нескольких значений, результат должен соответствовать всем ранее установленным условиям одновременно
    public NumberSchema range(int min, int max) {
        checkList.add(o -> (Integer) o >= min && (Integer) o <= max);
        return this;
    }

    @Override
    protected boolean isNullOrEmpty(Object o) {
        return o == null;
    }

    protected boolean checkInstance(Object o) {
        return o instanceof Integer;
    }
}
