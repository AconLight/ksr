package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.Feature;

import java.util.ArrayList;
import java.util.List;

public class SentimentExtractor implements IFeatureExtractor {
    @Override
    public List<Feature> extract(ClassifiedObject object) {
        List<Feature> features = new ArrayList<>();
        features.add(new Feature(0., "average sentence sentiment"));
        features.add(new Feature(0., "min sentence sentiment"));
        features.add(new Feature(0., "max sentence sentiment"));
        return features;
    }
}
