package runner;

import core.Knn;
import dataOperations.FeatureVector;
import metrics.IMetric;

import java.util.ArrayList;
import java.util.List;

public class KNNRunner extends Configurable {

    private List<List<Knn>> knn;

    private List<List<List<FeatureVector>>> initiatingVectors;

    private List<IMetric> metrics;

    private List<Result> resultsList;

    public KNNRunner(List<Result> resultsList, List<List<Knn>> knn, List<List<List<FeatureVector>>> initiatingVectors, List<IMetric> metrics) {
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
        for (List<List<FeatureVector>> vectors: initiatingVectors) {
            List<Knn> knns;
            knn.add(knns = new ArrayList<>());
            for (IMetric metric : metrics) {
                //System.out.println("knn start");
                String metricStr = metric.getClass().getName();
                for (List<FeatureVector> vector2 : vectors) {
                    Knn myKnn;
                    List<FeatureVector> vectorsNotUsa = new ArrayList<>();
                    for (FeatureVector v :vector2){
                        if (v.label != "usa") vectorsNotUsa.add(v);
                    }
                    knns.add(myKnn = new Knn(RunnerConfig.k[i/RunnerConfig.dataSetsRange], vectorsNotUsa, metric));
                    for (FeatureVector vector : vector2) {
                        String res = myKnn.evaluateAndAddToDataset(vector);
                        Result r = new Result();
                        r.k = " k in KNN: ( " + myKnn.k + ")";
                        r.dataSets = "data set: " + vector.origin;
                        r.keyWords = " key words extraction method: (" + vector.keyWordsMethod + ")";
                        r.wordSimilarity = " word similarity: " + vector.similarity + ")";
                        r.extractorsWithVariants = " features: " + vector.getFeatureNamesToString() + ")";
                        r.originalLabel = vector.label;
                        r.knn = res;
                        r.metric = " knn metric: ( " + metricStr + ")";
                        resultsList.add(r);
                    }
                }
                //System.out.println("knn finished");
            }

        }
    }
}
