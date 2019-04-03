package runner;

import classifiedObjects.Article;
import dataOperations.textStatistics.KeyWordExtractor;
import utils.ExtractionMethod;

import java.util.List;
import java.util.Map;

public class ExtractingKeyWords extends Configurable {

    private int N = 3;

    private List<List<Article>> articles;

    private List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public ExtractingKeyWords(int range, List<List<Article>> articles, List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList) {
        super(range);
        this.articles = articles;
        this.keyWordsList = keyWordsList;
    }

    @Override
    public void perform(int i) {
        keyWordsList.add(KeyWordExtractor.extractTopN(articles.get(i), N));
    }
}