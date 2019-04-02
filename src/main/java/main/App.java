package main;

import classifiedObjects.Article;
import config.Config;
import dataOperations.preprocessing.IPreprocessor;
import dataOperations.textStatistics.KeyWordExtractor;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.preprocessing.FullPreprocessor;
import runner.ExtractingFeature;
import utils.ExtractionMethod;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
//        ReutersArticleReader reader = new ReutersArticleReader();
//        DataLoader<Article> loader = new DataLoader<>();
//        Preprocessor preprocessor = new Preprocessor();
//
//        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);
//
//        List<IFeatureExtractor> subExtractors = new ArrayList<>();
//        subExtractors.add(new SentimentExtractor());
//
//        MainFeatureExtractor<Article> extractor = new MainFeatureExtractor<>(subExtractors);
//        System.out.println(extractor.extractWithTFIDF(articles.get(0)));

        DataLoader<Article> loader = new DataLoader<>();
        IPreprocessor<String> preprocessor = new FullPreprocessor();
        ReutersArticleReader reader = new ReutersArticleReader(preprocessor);

        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);

        Map<ExtractionMethod, Map<String, List<String>>> keyWords = KeyWordExtractor.extractTopN(articles, 10);
        for (ExtractionMethod label : keyWords.keySet()) {
            System.out.println(label);
            System.out.println(keyWords.get(label));
        }


//
//
//        List<TextStatistics> s1 = new ArrayList<>();
//        for (Article a : articles) {
//            if (a.getLabel().equals("usa")) {
//                List<String> words = preprocessor.lemme(a.getText());
//                words = preprocessor.applyStopList(words);
//                s1.add(new TextStatistics(words));
//            }
//        }
//
//        TextSetStatistics ts1 = new TextSetStatistics(s1);
//        System.out.println(ts1);
//        System.out.println(articles.size());
    }
}
