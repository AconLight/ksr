package dataOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FeatureVector {
    public FeatureVector(String label) {
        this.label = label;
    }

    public String origin;
    public String keyWordsMethod;
    public String similarity;

    public FeatureVector() {
        this.label = "not specified yet";
    }

    public List<String> getFeatureNames() {
        List<String> featureNames = new ArrayList<>();
        featureNames.sort(String::compareTo);
        return featureNames;
    }

    public String getFeatureNamesToString() {
        String res = "";
        for (Feature feature: features) {
            res += feature.getName() + "(keywords extraction: " + ", word similarity: " + similarity + ") ";
        }
        return res;
    }

    public void updateValues(List<Double> maxAbsValues) {
        for (int i = 0; i < features.size(); i++) {
            features.get(i).divideValue(maxAbsValues.get(i));
        }
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

    public List<Double> toList() {
        List<Double> featureValues = new ArrayList<>();
        for (Feature f : features) {
            featureValues.add(f.getValue());
        }
        return featureValues;
    }

    public String label;

    private List<Feature> features = new ArrayList<>();

    @Override
    public String toString() {
        return "FeatureVector [ " +
                "label='" + label + '\'' +
                ",\nfeatures=" + features +
                ']';
    }
}
