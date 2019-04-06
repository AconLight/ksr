package runner;

// it provides basic configurations for running the program
// it should enable us to automatically create SPRAWOZDANIE

import classifiedObjects.Article;
import core.Knn;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.MainFeatureExtractor;
import metrics.IMetric;
import utils.ExtractionMethod;

import java.util.*;

public class Runner {
    private List<Configurable> operations;
    private RunnerConfig runnerConfig;
    private List<List<Article>> articlesList;
    private List<List<Article>> articlesListTest;
    private List<List<Article>> articlesListTrain;
    private List<List<List<FeatureVector>>> featuresLists;
    private List<List<Knn>> knnList;
    private List<Result> results;
    private ArrayList<String> dataSets;
    private KeyWordsData keyWordsData = new KeyWordsData();

    public Runner() {
        runnerConfig = new RunnerConfig();
        articlesList = new ArrayList<>();
        articlesListTest = new ArrayList<>();
        articlesListTrain = new ArrayList<>();
        featuresLists = new ArrayList<>();
        keyWordsData.keyWordsList = new ArrayList<>();
        keyWordsData.origin = new ArrayList<>();
        operations = new ArrayList<>();
        knnList = new ArrayList<>();
        results = new ArrayList<>();
        dataSets = new ArrayList<>();

        operations.add(new ExtractingDataSets(dataSets, articlesList, articlesListTest, articlesListTrain));
        operations.add(new ExtractingKeyWords(RunnerConfig.dataSetsRange, articlesListTrain, keyWordsData));
        operations.add(new ExtractingFeatures(articlesListTrain, featuresLists, keyWordsData));
        operations.add(new KNNRunner(results, knnList, featuresLists, Arrays.asList(RunnerConfig.metrics)));

    }

    public void perform() {
        for (Configurable op: operations) {
            op.performAll();
        }
        System.out.println("results:");
        for (Result r: results) {
            System.out.println(r.toString());
        }

        HashMap<String, List<Result>> experiments = new HashMap<>();

        for (Result r: results) {
            if (experiments.get((r.dataSets + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants)) == null) {
                List<Result> newR;
                experiments.put((r.dataSets + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants), newR = new ArrayList<>());
                newR.add(r);
            } else {
                experiments.get((r.dataSets + r.k + r.metric + r.wordSimilarity + r.keyWords + r.extractorsWithVariants)).add(r);
            }
        }

        System.out.println("results by experiment:");
        for (Map.Entry<String, List<Result>> entry: experiments.entrySet()) {
            System.out.println(entry.getKey());
            float good = 0;
            float bad = 0;
            HashMap<String, Integer> confMatrix = new HashMap<>();
            HashMap<String, Integer> originalLabelsCount = new HashMap<>();
            HashMap<String, Integer> positives = new HashMap<>();
            HashMap<String, Integer> negatives = new HashMap<>();
            HashMap<String, Float> precision = new HashMap<>();
            HashMap<String, Float> recall = new HashMap<>();
            for (Result r: entry.getValue()) {
                if (r.knn == r.originalLabel) good++;
                else bad++;
                if (confMatrix.get((r.knn + " " + r.originalLabel)) == null) {
                    confMatrix.put((r.knn + " " + r.originalLabel), 1);
                } else {
                    confMatrix.put((r.knn + " " + r.originalLabel), confMatrix.get((r.knn + " " + r.originalLabel)) + 1);
                }

                if (originalLabelsCount.get((r.originalLabel)) == null) {
                    originalLabelsCount.put((r.originalLabel), 1);
                } else {
                    originalLabelsCount.put((r.originalLabel), originalLabelsCount.get((r.originalLabel)) + 1);
                }

                if (positives.get((r.originalLabel)) == null) {
                    if (r.originalLabel.equalsIgnoreCase(r.knn))
                        positives.put((r.originalLabel), 1);
                    else
                        positives.put((r.originalLabel), 0);
                } else if (r.originalLabel.equalsIgnoreCase(r.knn)) {
                    positives.put((r.originalLabel), positives.get((r.originalLabel))+1);
                }

                if (negatives.get((r.knn)) == null) {
                    if (!r.originalLabel.equalsIgnoreCase(r.knn))
                        negatives.put((r.knn), 1);
                    else
                        negatives.put((r.knn), 0);
                } else if (!r.knn.equalsIgnoreCase(r.knn)) {
                    negatives.put((r.knn), negatives.get((r.knn))+1);
                }

            }

            for (Map.Entry<String, Integer> posEntry: positives.entrySet()) {
                if (posEntry.getValue() == null) {
                    precision.put(posEntry.getKey(), 0f);
                } else {
                    float neg = 0;
                    if (negatives.get(posEntry.getKey()) != null) {
                        neg = negatives.get(posEntry.getKey());
                    }
                    precision.put(posEntry.getKey(), (1f * posEntry.getValue() / (posEntry.getValue() + neg)));
                }
            }

            for (Map.Entry<String, Integer> posEntry: positives.entrySet()) {
                if (posEntry.getValue() == null) {
                    recall.put(posEntry.getKey(), 0f);
                } else {
                    recall.put(posEntry.getKey(), (1f * posEntry.getValue() / originalLabelsCount.get(posEntry.getKey())));
                }
            }
            System.out.println("precision");
            System.out.println(precision);
            System.out.println();
            System.out.println("recall");
            System.out.println(recall);
            System.out.println();
        }

    }


    public static void main(String[] args) {
        Runner r = new Runner();
        r.perform();
    }



}
