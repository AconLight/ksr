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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Runner {
    private List<Configurable> operations;
    private RunnerConfig runnerConfig;
    private List<List<Article>> articlesList;
    private List<List<Article>> articlesListTest;
    private List<List<Article>> articlesListTrain;
    private List<List<FeatureVector>> featuresLists;
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
        operations = new ArrayList<>();
        knnList = new ArrayList<>();
        results = new ArrayList<>();
        dataSets = new ArrayList<>();

        operations.add(new ExtractingDataSets(dataSets, 1, articlesList, articlesListTest, articlesListTrain));
        operations.add(new ExtractingKeyWords(1, articlesListTrain, keyWordsData));
        operations.add(new ExtractingFeatures(articlesListTrain, 0, featuresLists, keyWordsData));
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

    }


    public static void main(String[] args) {
        Runner r = new Runner();
        r.perform();
    }



}
