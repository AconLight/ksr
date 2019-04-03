package metrics;

import dataOperations.FeatureVector;

public class ChebyshevMetric implements IMetric {
    @Override
    public Double measure(FeatureVector v1, FeatureVector v2) {
        double distance = 0;
        int fvSize = v1.getFeatures().size();
        for (int i = 0; i < fvSize; i++) {
            distance = Math.max(distance, Math.abs(v1.getFeatures().get(i).getValue() - v2.getFeatures().get(i).getValue()));
        }
        return distance;
    }
}
