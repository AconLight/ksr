package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.Feature;

import java.util.List;

public interface IFeatureExtractor {
    List<Feature> extract(ClassifiedObject object);
}
