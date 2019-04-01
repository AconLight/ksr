package dataOperations.textStatistics;

import java.util.*;

import static utils.Utils.sortIntMap;

public class TextStatistics {
    private Map<String, Integer> wordOccurrencesCount;
    private int uniqueWords;
    private int allWords;
    private double wordRepetitiveness;

    public TextStatistics(List<String> words) {
        wordOccurrencesCount = wordOccurrencesCount(words);
        allWords = words.size();
        uniqueWords = wordOccurrencesCount.size();
        wordRepetitiveness = (double) uniqueWords / allWords;
    }

    private Map<String, Integer> wordOccurrencesCount(List<String> words) {
        Map<String, Integer> wordOccurrencesCount = new HashMap<>();
        words.forEach(w -> {
            if (!wordOccurrencesCount.containsKey(w)) {
                wordOccurrencesCount.put(w, 1);
            } else {
                wordOccurrencesCount.put(w, wordOccurrencesCount.get(w) + 1);
            }
        });

        return sortIntMap(wordOccurrencesCount);
    }

    public Map<String, Integer> getWordOccurrencesCount() {
        return wordOccurrencesCount;
    }

    public int getUniqueWords() {
        return uniqueWords;
    }

    public int getAllWords() {
        return allWords;
    }

    public double getWordRepetitiveness() {
        return wordRepetitiveness;
    }

    @Override
    public String toString() {
        return "TextStatistics {" +
                "\n\twordOccurrencesCount=" + wordOccurrencesCount +
                ",\n\tuniqueWords=" + uniqueWords +
                ",\n\tallWords=" + allWords +
                ",\n\twordRepetitiveness=" + wordRepetitiveness +
                "\n}";
    }
}
