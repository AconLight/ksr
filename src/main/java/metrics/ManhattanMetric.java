package metrics;

import dataOperations.FeatureVector;

public class ManhattanMetric implements IMetric {

    @Override
    public Double measure(FeatureVector v1, FeatureVector v2) {
        double sum = 0;
        int fvSize = v1.getFeatures().size();
        for (int i = 0; i < fvSize; i++) {
            sum += Math.abs(v1.getFeatures().get(i).getValue() - v2.getFeatures().get(i).getValue());
        }
        return sum;
    }
}
