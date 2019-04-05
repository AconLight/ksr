package runner;

import core.Knn;
import dataOperations.FeatureVector;
import metrics.IMetric;

import java.util.ArrayList;
import java.util.List;

public class KNNRunner extends Configurable {

    private List<List<Knn>> knn;

    private List<List<FeatureVector>> initiatingVectors;

    private List<IMetric> metrics;

    public KNNRunner(List<List<Knn>> knn, List<List<FeatureVector>> initiatingVectors, List<IMetric> metrics) {
        super(RunnerConfig.k.length);
        this.initiatingVectors = initiatingVectors;
        this.knn = knn;
        this.metrics = metrics;
    }

    @Override
    public void perform(int i) {
        int n = 0;
        for (List<FeatureVector> vectors: initiatingVectors) {
            List<Knn> knns;
            knn.add(knns = new ArrayList<>());
            for (IMetric metric : metrics) {
                //System.out.println("knn start");
                Knn myKnn;
                List<FeatureVector> vectorsNotUsa = new ArrayList<>();
                for (FeatureVector v :vectors){
                    if (v.label != "usa") vectorsNotUsa.add(v);
                }
                knns.add(myKnn = new Knn(RunnerConfig.k[i], vectorsNotUsa, metric));
                for (FeatureVector vector : vectors) {
                    myKnn.evaluateAndAddToDataset(vector);
                }
                //System.out.println("knn finished");
            }

        }
    }
}
