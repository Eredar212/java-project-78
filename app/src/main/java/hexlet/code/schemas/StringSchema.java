package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<StringSchema> {
    private Integer minLength;
    private String containedTest = "";

    public StringSchema minLength(int length) {
        this.minLength = length;
        needCheckMethods.add("checkMinLength");
        return this;
    }
    public StringSchema contains(String text) {
        this.containedTest = text;
        needCheckMethods.add("checkContains");
        return this;
    }

    private boolean checkMinLength(Object tested) {
        return ((String) tested).length() >= this.minLength;
    }
    private boolean checkContains(Object tested) {
        return containedTest.isEmpty() || ((String) tested).contains(containedTest);
    }
    private static boolean checkInstance(Object o) {
        return o instanceof String || o == null;
    }
    //для строки метод checkRequired переопределен, потому что пустая строка тоже не должна проходить условие required
    private boolean checkRequired(Object tested) {
        return tested != null && !tested.toString().isEmpty();
    }
}
