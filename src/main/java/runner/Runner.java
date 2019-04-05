package runner;

// it provides basic configurations for running the program
// it should enable us to automatically create SPRAWOZDANIE

import classifiedObjects.Article;
import core.Knn;
import dataOperations.Feature;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.MainFeatureExtractor;
import metrics.IMetric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    private List<Configurable> operations;
    private RunnerConfig runnerConfig;
    private List<List<Article>> articlesList;
    private List<List<Article>> articlesListTest;
    private List<List<Article>> articlesListTrain;
    private List<List<FeatureVector>> featuresLists;
    private List<Knn> knnList;


    public Runner() {
        runnerConfig = new RunnerConfig();
        articlesList = new ArrayList<>();
        articlesListTest = new ArrayList<>();
        articlesListTrain = new ArrayList<>();
        featuresLists = new ArrayList<>();
        RunnerConfig.keyWordsList = new ArrayList<>();
        operations = new ArrayList<>();
        knnList = new ArrayList<>();


        operations.add(new ExtractingDataSets(1, articlesList, articlesListTest, articlesListTrain));
        operations.add(new ExtractingKeyWords(1, articlesListTrain, RunnerConfig.keyWordsList));
        operations.add(new ExtractingFeatures(articlesList, 0, featuresLists, RunnerConfig.keyWordsList));
        operations.add(new KNNRunner(knnList, featuresLists, Arrays.asList(RunnerConfig.metrics)));

        System.out.println(operations.get(1).range);
    }

    public void perform() {
        for (Configurable op: operations) {
            op.performAll();
        }
    }


    public static void main(String[] args) {
        Runner r = new Runner();
        r.perform();
    }



}
