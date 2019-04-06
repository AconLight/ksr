package core;


import dataOperations.Feature;
import dataOperations.FeatureVector;
import metrics.IMetric;

import java.util.*;

import static utils.Utils.getTopNFromIntMap;
import static utils.Utils.sortIntMap;

public class Knn {
    public int k;
    private FeatureDataset dataset;
    private IMetric metric;

    public Knn(int k, List<FeatureVector> initiatingVectors, IMetric metric) {
        this.k = k;
        this.metric = metric;
        dataset = new FeatureDataset(initiatingVectors);
    }

    public String evaluate(FeatureVector featureVector) {
        List<FeatureVector> kClosest = findKClosestVectors(featureVector);
        Map<String, Integer> labelByNumberOfVectors = new HashMap<>();

        for (FeatureVector fv : kClosest) {
            if (!labelByNumberOfVectors.containsKey(fv.getLabel())) {
                labelByNumberOfVectors.put(fv.getLabel(), 1);
            } else {
                labelByNumberOfVectors.put(fv.getLabel(), labelByNumberOfVectors.get(fv.getLabel()) + 1);
            }
        }
        sortIntMap(labelByNumberOfVectors);
        return getTopNFromIntMap(labelByNumberOfVectors, 1).get(0);
    }

    public String evaluateAndAddToDataset(FeatureVector featureVector) {
        String result = evaluate(featureVector);
        FeatureVector copiedVector = new FeatureVector(result);
        copiedVector.addFeatures(featureVector.getFeatures());
        dataset.addFeatureVector(copiedVector);
        return result;
    }

    private List<FeatureVector> findKClosestVectors(FeatureVector featureVector) {
        Queue<FeatureVector> closestFeatureVectors = new PriorityQueue<>(k, (o1, o2) -> {
            double distance1 = metric.measure(featureVector, o1);
            double distance2 = metric.measure(featureVector, o2);
            return Double.compare(distance2, distance1);
        });

        for (FeatureVector fv : dataset.getFeatureVectors()) {
            closestFeatureVectors.add(fv);
            if (closestFeatureVectors.size() > k) {
                closestFeatureVectors.remove();
            }
        }

        return new ArrayList<>(closestFeatureVectors);
    }
}
