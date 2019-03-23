package dataOperations.dataLoading.featureExtractors;

import dataOperations.ClassifiedObject;
import dataOperations.Feature;

public interface IFeatureExtractor {
    Feature extract(ClassifiedObject object);
}
