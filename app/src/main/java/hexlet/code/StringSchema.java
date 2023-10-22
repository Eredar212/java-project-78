package hexlet.code;

public class StringSchema {
    private boolean required = false;
    private Integer minLength;
    private String containedTest = "";

    public StringSchema required() {
        this.required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String text) {
        this.containedTest = text;
        return this;
    }

    public boolean isValid(Object tested) {
        if (!(tested instanceof String) && tested != null) {
            return false;
        }
        String testedText = tested == null ? "" : tested.toString();
        if (testedText.isEmpty() && required) {
            return false;
        }
        if (this.minLength != null && testedText.length() < this.minLength) {
            return false;
        }
        return containedTest.isEmpty() || testedText.contains(containedTest);
    }
}
