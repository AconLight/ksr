package dataOperations.featureExtractors;

import classifiedObjects.ClassifiedObject;
import dataOperations.Feature;
import utils.ExtractionMethod;
import word.similarity.IWordSimilarity;

import java.util.List;

public interface IFeatureExtractor<T extends ClassifiedObject> {
    void setVariant(int i);
    List<Feature> extract(T object);
    void setData(ExtractorData data);

    IWordSimilarity similarityMethod(int i);
    ExtractionMethod extractionMethod(int i);
}
