package runner;

import core.Knn;
import dataOperations.FeatureVector;
import metrics.IMetric;

import java.util.*;

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
                    Map<String, Integer> vectorsClassCount = new HashMap<>();
                    List<FeatureVector> vecClass = new ArrayList<>();
                    for (FeatureVector vec: vector2) {
                        if (vectorsClassCount.get(vec.label) == null) {
                            vectorsClassCount.put(vec.label, 1);
                            vecClass.add(vec);
                        }
                        else if (vectorsClassCount.get(vec.label) < 5) {
                            vectorsClassCount.put(vec.label, vectorsClassCount.get(vec.label) + 1);
                            vecClass.add(vec);
                        }
                    }
                    knns.add(myKnn = new Knn(RunnerConfig.k[i/RunnerConfig.dataSetsRange], vecClass, metric));
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
