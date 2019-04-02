package dataOperations.textStatistics;

import classifiedObjects.Article;

import java.util.*;

import static utils.Utils.sortDoubleMap;

public class KeyWordExtractor {
    public static HashMap<String, Map<String, Double>> getTopN(Map<String, Map<String, Double>> wordWeightsByLabel, int n) {
        HashMap<String, Map<String, Double>> topNByLabels = new HashMap();
        for (String label: wordWeightsByLabel.keySet()) {
            Map<String, Double> toAdd = new HashMap<>();
            int i = 0;
            for (Map.Entry<String, Double> entry: wordWeightsByLabel.get(label).entrySet()) {
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
