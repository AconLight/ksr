package dataOperations;

public class Feature {
    private Double value;
    private String name;

    public Feature(Double value, String name) {
        this.value = value;
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void divideValue(double divisor) {
        value /= divisor;
    }

    @Override
    public String toString() {
        return "{" + name + " : " + value + "}";
    }
}
