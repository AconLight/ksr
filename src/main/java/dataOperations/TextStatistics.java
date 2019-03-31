package dataOperations;

import java.util.*;
import java.util.stream.Collectors;

public class TextStatistics {
    public static Map<String, Integer> wordOccurrences(List<String> words) {
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
}
