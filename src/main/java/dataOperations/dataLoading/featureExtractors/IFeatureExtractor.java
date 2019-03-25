package dataOperations.dataLoading.featureExtractors;

import dataOperations.classifiedObjects.ClassifiedObject;
import dataOperations.Feature;

public interface IFeatureExtractor {
    Feature extract(ClassifiedObject object);
}
