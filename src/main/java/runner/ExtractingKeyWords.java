package runner;

import classifiedObjects.Article;
import dataOperations.textStatistics.KeyWordExtractor;
import utils.ExtractionMethod;

import java.util.List;
import java.util.Map;

public class ExtractingKeyWords extends Configurable {

    private int N = 20;

    private List<List<Article>> articles;

    private KeyWordsData keyWordsData;

    private List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public ExtractingKeyWords(int range, List<List<Article>> articles, KeyWordsData keyWordsdata) {
        super(range);
        this.articles = articles;
        this.keyWordsList = keyWordsdata.keyWordsList;
        this.keyWordsData = keyWordsdata;
    }

    @Override
    public void perform(int i) {
        keyWordsList.add(KeyWordExtractor.extractTopN(articles.get(i), N));
        keyWordsData.origin.add(articles.get(i).get(0).origin);
        System.out.println(articles.get(i).get(0).origin);
        System.out.println(i);
    }
}