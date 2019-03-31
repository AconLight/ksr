package dataOperations;

import java.util.ArrayList;
import java.util.List;

public class FeatureVector {
    public FeatureVector(String label) {
        this.label = label;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }

    public void addFeatures(List<Feature> features) {
        this.features.addAll(features);
    }

    private String label;

    private List<Feature> features = new ArrayList<>();
}
