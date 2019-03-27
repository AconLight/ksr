package main;

import classifiedObjects.Article;
import config.Config;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;
import dataOperations.Preprocessing;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ReutersArticleReader reader = new ReutersArticleReader();
        DataLoader<Article> loader = new DataLoader<>();

        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);
        Article a = articles.get(0);

        System.out.println(a.getText());
        System.out.println(Preprocessing.cleanse(a.getText()));

        System.out.println(articles.size());
    }
}
