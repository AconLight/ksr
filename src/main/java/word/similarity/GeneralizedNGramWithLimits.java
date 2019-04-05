package word.similarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GeneralizedNGramWithLimits implements IWordSimilarity {
    private List<NGram> ngrams = new ArrayList<>();

    public GeneralizedNGramWithLimits(int lowerLimit, int upperLimit) {
        assert lowerLimit <= upperLimit;
        for (int i = lowerLimit; i <= upperLimit; i++) {
            ngrams.add(new NGram(i));
        }
    }

    @Override
    public Double measure(String s1, String s2) {
        double commonSubparts = 0;
        double possibleSubparts = 0;

        String longer, shorter;
        if (s1.length() > s2.length()) {
            longer = s1;
            shorter = s2;
        } else {
            longer = s2;
            shorter = s1;
        }


        for (NGram ngram : ngrams) {
            Set<String> possibleSubstrings = ngram.possibleSubstrings(longer);
            commonSubparts += ngram.commonSubstrings(shorter, possibleSubstrings);
            possibleSubparts += possibleSubstrings.size();
        }

        return commonSubparts / possibleSubparts;
    }

}
