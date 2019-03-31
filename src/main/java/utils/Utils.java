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
    public static Map<String, Integer> sortMap(Map<String, Integer> map) {
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
