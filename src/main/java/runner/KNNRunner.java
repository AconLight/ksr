package runner;

import core.Knn;
import dataOperations.FeatureVector;
import metrics.IMetric;

import java.util.List;

public class KNNRunner extends Configurable {

    int[] k = { 1, 2, 3, 5, 8};

    private List<Knn> knn;

    private List<FeatureVector> initiatingVectors;

    private List<IMetric> metrics;

    public KNNRunner(List<Knn> knn, List<FeatureVector> initiatingVectors, List<IMetric> metrics) {
        this.initiatingVectors = initiatingVectors;
        this.knn = knn;
        this.metrics = metrics;
    }

    @Override
    public void perform(int i) {
        for (IMetric metric: metrics) {
            knn.add(new Knn(i, initiatingVectors, metric));
        }
    }
}
