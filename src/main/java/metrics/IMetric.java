package metrics;

import dataOperations.FeatureVector;

public interface IMetric {
    Double measure(FeatureVector v1, FeatureVector v2);
}
