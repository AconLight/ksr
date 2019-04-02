package dataOperations.textStatistics;

import classifiedObjects.Article;
import utils.ExtractionMethod;
import utils.Utils;

import java.util.*;

import static utils.Utils.*;

public class KeyWordExtractor {

    public static Map<ExtractionMethod, Map<String, List<String>>> extractTopN(List<Article> articles, int N) {
        Map<ExtractionMethod, Map<String, List<String>>> extractorToLabels = new HashMap<>();
        extractorToLabels.put(ExtractionMethod.TFIDF, Utils.getTopNFromWeightedKeywordMap(extractWithTFIDF(articles), N));

        Map<String, List<Article>> labelToArticles = splitByLabel(articles);


        extractorToLabels.put(ExtractionMethod.OVERALL_WORD_COUNT, new HashMap<>());
        extractorToLabels.put(ExtractionMethod.OVERALL_WORD_OCCURRENCES, new HashMap<>());

        for(String label: labelToArticles.keySet()){
            TextSetStatistics setStatistics = articlesToTextSetStats(labelToArticles.get(label));
            extractorToLabels.get(ExtractionMethod.OVERALL_WORD_COUNT).put(label, getTopNFromIntMap(setStatistics.getWordSummedOccurrences(), N));
            extractorToLabels.get(ExtractionMethod.OVERALL_WORD_OCCURRENCES).put(label, getTopNFromIntMap(setStatistics.getWordOverallOccurrences(), N));
        }

        return extractorToLabels;
    }

    private static Map<String, List<Article>> splitByLabel(List<Article> articles) {
        Map<String, List<Article>> tokenToArticles = new HashMap<>();
        for (Article a : articles) {
            if (!tokenToArticles.keySet().contains(a.getLabel())) {
                tokenToArticles.put(a.getLabel(), new ArrayList<>());
            }
            tokenToArticles.get(a.getLabel()).add(a);
        }
        return tokenToArticles;
    }

    public static Map<String, Integer> extractWithOverallWordCount(List<Article> articles) {
        TextSetStatistics setStatistics = articlesToTextSetStats(articles);
        return setStatistics.getWordSummedOccurrences();
    }

    public static Map<String, Integer> extractWithWordOccurenceCount(List<Article> articles) {
        TextSetStatistics setStatistics = articlesToTextSetStats(articles);
        return setStatistics.getWordOverallOccurrences();
    }

    private static TextSetStatistics articlesToTextSetStats(List<Article> articles) {
        List<TextStatistics> stats = new ArrayList<>();
        for (Article a : articles) {
            stats.add(new TextStatistics(a.getTextWords()));
        }
        return new TextSetStatistics(stats);
    }

    public static Map<String, Map<String, Double>> getTopN(Map<String, Map<String, Double>> wordWeightsByLabel, int n) {
        Map<String, Map<String, Double>> topNByLabels = new HashMap<>();
        for (String label : wordWeightsByLabel.keySet()) {
            Map<String, Double> toAdd = new HashMap<>();
            int i = 0;
            for (Map.Entry<String, Double> entry : wordWeightsByLabel.get(label).entrySet()) {
                if (i >= n) {
                    break;
                }
                toAdd.put(entry.getKey(), entry.getValue());
                i++;
            }
            topNByLabels.put(label, toAdd);
        }
        return topNByLabels;
    }

    public static Map<String, Map<String, Double>> extractWithTFIDF(List<Article> articles) {
        Map<String, Map<String, Double>> wordWeightsByLabel = new HashMap<>();

        Map<String, List<List<String>>> articlesByLabel = new HashMap<>();
        for (Article a : articles) {
            if (!articlesByLabel.containsKey(a.getLabel())) {
                articlesByLabel.put(a.getLabel(), new ArrayList<>());
            }
            articlesByLabel.get(a.getLabel()).add(a.getTextWords());
        }

        TFIDFCalculator calculator = new TFIDFCalculator();
        for (String label : articlesByLabel.keySet()) {
            wordWeightsByLabel.put(label, new HashMap<>());
            List<String> articlesCombined = combineWords(articlesByLabel.get(label));

            List<List<String>> otherArticles = new ArrayList<>();
            for (String otherLabel : articlesByLabel.keySet()) {
                if (!otherLabel.equals(label)) {
                    otherArticles.addAll(articlesByLabel.get(otherLabel));
                }
            }

            Set<String> uniqueWords = new HashSet<>(articlesCombined);
            for (String word : uniqueWords) {
                wordWeightsByLabel.get(label).put(word, calculator.tfIdf(articlesCombined, otherArticles, word));
            }
            wordWeightsByLabel.put(label, sortDoubleMap(wordWeightsByLabel.get(label)));
        }

        return wordWeightsByLabel;
    }

    private static List<String> combineWords(List<List<String>> wordLists) {
        List<String> combinedWords = new ArrayList<>();
        for (List<String> wordList : wordLists) {
            combinedWords.addAll(wordList);
        }
        return combinedWords;
    }
}
