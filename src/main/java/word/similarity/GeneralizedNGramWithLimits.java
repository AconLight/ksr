package word.similarity;

import java.util.ArrayList;
import java.util.List;

public class GeneralizedNGramWithLimits implements IWordSimilarity {
    private List<NGram> ngrams = new ArrayList<>();

    private int lowerLimit, upperLimit;

    public GeneralizedNGramWithLimits(int lowerLimit, int upperLimit) {
        assert lowerLimit <= upperLimit;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        for (int i = lowerLimit; i <= upperLimit; i++) {
            ngrams.add(new NGram(i));
        }
    }

    @Override
    public Double measure(String s1, String s2) {
        double sum = 0;
        for (NGram ngram : ngrams) {
            sum += ngram.g(s1, s2);
        }
        int denominator = possibleCombinationsCount(s1, s2, lowerLimit, upperLimit);
        if (denominator == 0) {
            return 0.;
        }
        return sum / denominator;
    }

    private int possibleCombinationsCount(String s1, String s2, int lowerLimit, int upperLimit) {
        int N = Math.max(s1.length(), s2.length());
        int denominator = ((N - lowerLimit + 1) * (N - lowerLimit + 2) - (N - upperLimit + 1) * (N - upperLimit));
        if (denominator == 0) {
            return 0;
        }
        return 2 / denominator;
    }
}
