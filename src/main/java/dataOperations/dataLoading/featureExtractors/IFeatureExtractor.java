package dataOperations.dataLoading.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.Feature;

public interface IFeatureExtractor {
    Feature extract(ClassifiedObject object);
}
