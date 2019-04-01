package dataOperations.textStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.sortIntMap;

public class TextSetStatistics {
    private double averageWordRepetitiveness;
    private double overallWordRepetitiveness;
    private double averageUniqueWordsCount;
    private double averageWordCount;

    private Map<String, Integer> wordOverallOccurrences;
    private Map<String, Integer> wordSummedOccurrences;

    public TextSetStatistics(List<TextStatistics> statisticsList) {
        averageWordRepetitiveness = averageWordRepetitiveness(statisticsList);
        overallWordRepetitiveness = overallWordRepetitiveness(statisticsList);
        averageUniqueWordsCount = averageUniqueWordsCount(statisticsList);
        averageWordCount = averageWordCount(statisticsList);
        wordOverallOccurrences = wordOverallOccurrences(statisticsList);
        wordSummedOccurrences = wordSummedOccurrences(statisticsList);
    }

    private Map<String, Integer> wordSummedOccurrences(List<TextStatistics> statisticsList) {
        Map<String, Integer> occurrences = new HashMap<>();
        for (TextStatistics statistics : statisticsList) {
            for (String word : statistics.getWordOccurrencesCount().keySet()) {
                if (!occurrences.containsKey(word)) {
                    occurrences.put(word, statistics.getWordOccurrencesCount().get(word));
                } else {
                    occurrences.put(word, occurrences.get(word) + statistics.getWordOccurrencesCount().get(word));
                }
            }
        }
        return sortIntMap(occurrences);
    }

    private Map<String, Integer> wordOverallOccurrences(List<TextStatistics> statisticsList) {
        Map<String, Integer> occurrences = new HashMap<>();
        for (TextStatistics statistics : statisticsList) {
            for (String word : statistics.getWordOccurrencesCount().keySet()) {
                if (!occurrences.containsKey(word)) {
                    occurrences.put(word, 1);
                } else {
                    occurrences.put(word, occurrences.get(word) + 1);
                }
            }
        }
        return sortIntMap(occurrences);
    }

    private double overallWordRepetitiveness(List<TextStatistics> statisticsList) {
        int uniqueWords = 0;
        int allWords = 0;
        for (TextStatistics ts : statisticsList) {
            uniqueWords += ts.getUniqueWords();
            allWords += ts.getAllWords();
        }
        return (double) uniqueWords / allWords;
    }

    private double averageWordRepetitiveness(List<TextStatistics> statisticsList) {
        double sum = statisticsList.stream().mapToDouble(TextStatistics::getWordRepetitiveness).sum();
        return sum / statisticsList.size();
    }

    private double averageUniqueWordsCount(List<TextStatistics> statisticsList) {
        double sum = statisticsList.stream().mapToInt(TextStatistics::getUniqueWords).sum();
        return sum / statisticsList.size();
    }

    private double averageWordCount(List<TextStatistics> statisticsList) {
        double sum = statisticsList.stream().mapToInt(TextStatistics::getAllWords).sum();
        return sum / statisticsList.size();
    }

    public Map<String, Integer> getWordOverallOccurrences() {
        return wordOverallOccurrences;
    }

    public Map<String, Integer> getWordSummedOccurrences() {
        return wordSummedOccurrences;
    }

    public double getAverageWordRepetitiveness() {
        return averageWordRepetitiveness;
    }

    public double getAverageUniqueWordsCount() {
        return averageUniqueWordsCount;
    }

    public double getAverageWordCount() {
        return averageWordCount;
    }


    @Override
    public String toString() {
        return "TextSetStatictics{" +
                "\n\taverageWordRepetitiveness=" + averageWordRepetitiveness +
                "\n\toverallWordRepetitiveness=" + overallWordRepetitiveness +
                ",\n\taverageUniqueWordsCount=" + averageUniqueWordsCount +
                ",\n\taverageWordCount=" + averageWordCount +
                ",\n\twordOverallOccurrences=" + wordOverallOccurrences +
                ",\n\twordSummedOccurrences=" + wordSummedOccurrences +
                "\n}";
    }
}
