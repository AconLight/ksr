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

    private List<Result> resultsList;

    public KNNRunner(List<Result> resultsList, List<List<Knn>> knn, List<List<FeatureVector>> initiatingVectors, List<IMetric> metrics) {
        super(RunnerConfig.k.length);
        this.initiatingVectors = initiatingVectors;
        this.knn = knn;
        this.metrics = metrics;
        this.resultsList = resultsList;
        this.range = initiatingVectors.size();
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
                    String res = myKnn.evaluateAndAddToDataset(vector);
                    Result r = new Result();
                    r.dataSets = vector.origin;
                    r.keyWords = vector.keyWordsMethod;
                    r.wordSimilarity = vector.similarity;
                    r.extractorsWithVariants = vector.toString();
                    r.originalLabel = vector.label;
                    r.knn = res;
                    resultsList.add(r);
                }
                //System.out.println("knn finished");
            }

        }
    }
}
