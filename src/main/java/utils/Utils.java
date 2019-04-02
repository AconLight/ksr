package utils;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.SentenceUtils;
import edu.stanford.nlp.process.DocumentPreprocessor;
import org.ejml.simple.SimpleMatrix;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static Map<String, List<String>> getTopNFromWeightedKeywordMap(Map<String, Map<String, Double>> map, int n) {
        Map<String, List<String>> labelToKeyWords = new HashMap<>();
        for (String label : map.keySet()) {
            labelToKeyWords.put(label, new ArrayList<>());
            Map<String, Double> wordToWeights = sortDoubleMap(map.get(label));
            labelToKeyWords.put(label, getTopNFromDoubleMap(wordToWeights, n));
        }
        return labelToKeyWords;
    }

    public static List<String> getTopNFromDoubleMap(Map<String, Double> map, int n) {
        List<String> topList = new ArrayList<>();
        int i = 0;
        for (String s : map.keySet()) {
            topList.add(s);
            i++;
            if (i >= n) {
                return topList;
            }
        }
        return topList;
    }
    public static List<String> getTopNFromIntMap(Map<String, Integer> map, int n) {
        List<String> topList = new ArrayList<>();
        int i = 0;
        for (String s : map.keySet()) {
            topList.add(s);
            i++;
            if (i >= n) {
                return topList;
            }
        }
        return topList;
    }

    public static String joinList(List<String> strings, String joiner) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
            builder.append(joiner);
        }
        return builder.toString();
    }

    public static Map<String, Integer> sortIntMap(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static Map<String, Double> sortDoubleMap(Map<String, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static List<String> textToSentences(String text) {
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        List<String> sentences = new ArrayList<>();

        for (List<HasWord> sentence : dp) {
            String sentenceString = SentenceUtils.listToString(sentence);
            sentences.add(sentenceString);
        }
        return sentences;
    }

    public static Double sentimentMatrixToDouble(SimpleMatrix sentimentMatrix) {
        double[] cellWeights = {-1, -0.5, 0, 0.5, 1};
        double sum = 0;
        for (int i = 0; i < cellWeights.length; i++) {
            sum += cellWeights[i] * sentimentMatrix.get(i);
        }
        return sum;
    }
}
