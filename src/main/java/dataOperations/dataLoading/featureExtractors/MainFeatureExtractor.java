package dataOperations.dataLoading.featureExtractors;

import dataOperations.ClassifiedObject;
import dataOperations.FeatureVector;

import java.util.List;

public class MainFeatureExtractor {
    public MainFeatureExtractor(List<IFeatureExtractor> extractors) {
        this.extractors = extractors;
    }

    public FeatureVector extract(ClassifiedObject object) {
        FeatureVector vector = new FeatureVector(object.getLabel());
        extractors.forEach(extractor -> vector.addFeature(extractor.extract(object)));
        return vector;
    }

    private List<IFeatureExtractor> extractors;
}
