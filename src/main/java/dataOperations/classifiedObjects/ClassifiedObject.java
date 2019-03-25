package dataOperations.classifiedObjects;

public abstract class ClassifiedObject {
    public ClassifiedObject(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
