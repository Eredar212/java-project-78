package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<StringSchema> {
    public StringSchema minLength(Integer length) {
        validation = validation.and(o -> length == null || ((String) o).length() >= length);
        return this;
    }

    public StringSchema contains(String text) {
        validation = validation.and(o -> text == null || ((String) o).contains(text));
        return this;
    }

    protected boolean checkInstance(Object o) {
        return o instanceof String || o == null;
    }
}
