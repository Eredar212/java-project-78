package hexlet.code;

public class BaseSchema {
    protected boolean required;

    public BaseSchema required() {
        this.required = true;
        return this;
    }
    public boolean isValid(Object tested) {
        return true;
    }
}
