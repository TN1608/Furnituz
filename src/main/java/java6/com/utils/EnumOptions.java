package java5.asm.utils;

public class EnumOptions {
    private String value;
    private String label;

    public EnumOptions(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}

