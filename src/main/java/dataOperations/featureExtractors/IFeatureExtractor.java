package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.Feature;

import java.util.List;

public interface IFeatureExtractor<T extends ClassifiedObject> {
    void setVariant(int i);
    List<Feature> extract(T object);
    void setData(ExtractorData data);
}
