package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.FeatureVector;

import java.util.List;

public class MainFeatureExtractor<T extends ClassifiedObject> {
    public MainFeatureExtractor(List<IFeatureExtractor<T>> extractors) {
        this.extractors = extractors;
    }

    public FeatureVector extract(T object) {
        FeatureVector vector = new FeatureVector(object.getLabel());
        extractors.forEach(extractor -> vector.addFeatures(extractor.extract(object)));
        return vector;
    }

    private List<IFeatureExtractor<T>> extractors;
}
