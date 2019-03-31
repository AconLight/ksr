package main;

import classifiedObjects.Article;
import config.Config;
import dataOperations.textStatistics.TextSetStatistics;
import dataOperations.textStatistics.TextStatistics;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.Preprocessor;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ReutersArticleReader reader = new ReutersArticleReader();
        DataLoader<Article> loader = new DataLoader<>();
        Preprocessor preprocessor = new Preprocessor();

        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);

        List<TextStatistics> s1 = new ArrayList<>();
        for (Article a : articles) {
            if (a.getLabel().equals("usa")) {
                List<String> words = preprocessor.lemme(a.getText());
                words = preprocessor.applyStopList(words);
                s1.add(new TextStatistics(words));
            }
        }

        TextSetStatistics ts1 = new TextSetStatistics(s1);
        System.out.println(ts1);
        System.out.println(articles.size());
    }
}
