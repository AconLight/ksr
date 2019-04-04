package main;

import classifiedObjects.Article;
import config.Config;
import core.Knn;
import dataOperations.FeatureVector;
import dataOperations.featureExtractors.*;
import dataOperations.preprocessing.IPreprocessor;
import dataOperations.textStatistics.KeyWordExtractor;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.preprocessing.FullPreprocessor;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.NGram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        int ngram = 1;
        int initSizePerClass = 35;
        int k = 3;
        int numberOfKeywords = 5;
        ExtractionMethod extractionMethod = ExtractionMethod.TFIDF;


        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        ReutersArticleReader reader = new ReutersArticleReader(preprocessor);

        List<Article> testSetArticles = loader.loadObjects(Config.learningSetPath, reader);

        Map<ExtractionMethod, Map<String, List<String>>> keyWords = KeyWordExtractor.extractTopN(testSetArticles, numberOfKeywords);
        for (ExtractionMethod label : keyWords.keySet()) {
            System.out.println(label);
            System.out.println(keyWords.get(label));
        }

        List<IFeatureExtractor<Article>> subExtractors = new ArrayList<>();
        IFeatureExtractor<Article> subExtractor = new AvgMaxKeyWordFeatureExtractor();
        subExtractor.setData(new ExtractorData(keyWords, new NGram(ngram), extractionMethod));
        subExtractors.add(subExtractor);

        MainFeatureExtractor<Article> extractor = new MainFeatureExtractor<>(subExtractors);
        List<Article> initiatingArticles = loader.loadNObjects(Config.initTestSetPath, reader, initSizePerClass, Config.placesLabels);
        List<Article> testArticles = loader.loadObjects(Config.testSetPath, reader);

        Knn knn = new Knn(k, extractor.extractAll(initiatingArticles), new ManhattanMetric());

        Map<String, Integer> confusionMatrix = new HashMap<>();

        int sum = 0;
        for (FeatureVector fv : extractor.extractAll(testArticles)) {
            String result = knn.evaluate(fv);
            String name = fv.getLabel() + " " + result;
            if (!confusionMatrix.containsKey(name)) {
                confusionMatrix.put(name, 0);
            }

            confusionMatrix.put(name, confusionMatrix.get(name) + 1);

            if (result.equals(fv.getLabel())) sum++;
        }

        System.out.println(confusionMatrix);

        double precision = (double) sum / testArticles.size();
        System.out.println("Precision: " + precision);

        StatWriter.write(new TestResults(ngram, initSizePerClass, k, numberOfKeywords, extractionMethod, precision));
    }
}
