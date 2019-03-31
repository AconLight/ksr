package dataOperations.textStatistics;

import java.util.*;
import java.util.stream.Collectors;

public class TextStatistics {
    private Map<String, Integer> wordOccurrences;
    private int uniqueWords;
    private int allWords;
    private double wordRepetitiveness;

    public TextStatistics(List<String> words) {
        wordOccurrences = wordOccurrences(words);
        allWords = words.size();
        uniqueWords = wordOccurrences.size();
        wordRepetitiveness = (double)uniqueWords / allWords;
    }

    private Map<String, Integer> wordOccurrences(List<String> words) {
        Map<String, Integer> wordOccurrences = new HashMap<>();
        words.forEach(w -> {
            if (!wordOccurrences.containsKey(w)) {
                wordOccurrences.put(w, 1);
            } else {
                wordOccurrences.put(w, wordOccurrences.get(w) + 1);
            }
        });

        return wordOccurrences.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> getWordOccurrences() {
        return wordOccurrences;
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
                "\n\twordOccurrences=" + wordOccurrences +
                ",\n\tuniqueWords=" + uniqueWords +
                ",\n\tallWords=" + allWords +
                ",\n\twordRepetitiveness=" + wordRepetitiveness +
                "\n}";
    }
}
