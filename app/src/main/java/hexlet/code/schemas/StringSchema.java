package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<StringSchema> {
    public StringSchema minLength(Integer length) {
        // null - условное значение вместо try catch
        // при установке нескольких значений результат проверки будет относительно самого строго (большего)
        checkList.add(o -> length == null || ((String) o).length() >= length);
        return this;
    }

    //при установке нескольких значений, результат должен соответствовать всем ранее установленным условиям одновременно
    public StringSchema contains(String text) {
        checkList.add(o -> text == null || ((String) o).contains(text));
        return this;
    }

    @Override
    protected boolean isNullOrEmpty(Object o) {
        return o == null || (o instanceof String && ((String) o).isEmpty());
    }

    protected boolean checkInstance(Object o) {
        return o instanceof String;
    }
}
