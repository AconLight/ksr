package runner;

// it provides basic configurations for running the program
// it should enable us to automatically create SPRAWOZDANIE

import classifiedObjects.Article;
import config.Config;
import dataOperations.Feature;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private List<Configurable> operations;
    private RunnerConfig runnerConfig;
    private List<List<Article>> articlesList;
    private List<List<Feature>> featuresLists;

    public Runner() {
        runnerConfig = new RunnerConfig();
        articlesList = new ArrayList<>();
        featuresLists = new ArrayList<>();
        operations = new ArrayList<>();
        operations.add(new ExtractingDataSets(1, articlesList));
        operations.add(new ExtractingFeatures(articlesList, 0, featuresLists));
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
