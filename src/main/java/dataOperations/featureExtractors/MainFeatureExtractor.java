package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.FeatureVector;

import java.util.ArrayList;
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

    public List<FeatureVector> extractAll(List<T> objects) {
        List<FeatureVector> featureVectors = new ArrayList<>();
        for (T object : objects) {
            featureVectors.add(extract(object));
        }
        return featureVectors;
    }


    private List<IFeatureExtractor<T>> extractors;
}
