package main;

import classifiedObjects.Article;
import config.Config;
import dataOperations.TextStatistics;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.Preprocessor;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ReutersArticleReader reader = new ReutersArticleReader();
        DataLoader<Article> loader = new DataLoader<>();
        Preprocessor preprocessor = new Preprocessor();

        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);
        Article a = articles.get(0);

        System.out.println(a.getText());
        List<String> words = preprocessor.lemme(a.getText());
        System.out.println(words);

        List<String> prunedWords = preprocessor.applyStopList(words);

        System.out.println(prunedWords);
        System.out.println(TextStatistics.wordOccurrences(words));
        System.out.println(TextStatistics.wordOccurrences(prunedWords));

    }
}
