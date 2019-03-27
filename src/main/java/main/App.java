package main;

import classifiedObjects.Article;
import config.Config;
import dataOperations.dataLoading.DataLoader;
import dataOperations.dataLoading.fileReaders.ReutersArticleReader;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ReutersArticleReader reader = new ReutersArticleReader();
        DataLoader<Article> loader = new DataLoader<>();

        List<Article> articles = loader.loadObjects(Config.learningSetPath, reader);
        for (Article a : articles) {
            System.out.println(a);
        }
        System.out.println(articles.size());
    }
}
