package word.similarity;

import java.util.HashSet;
import java.util.Set;

public class NGram implements IWordSimilarity {
    private int n;

    public NGram(int n) {
        this.n = n;
    }

    public Double measure(String s1, String s2) {
        String longer, shorter;
        if (s1.length() > s2.length()) {
            longer = s1;
            shorter = s2;
        } else {
            longer = s2;
            shorter = s1;
        }

        if (n > longer.length()) return 0.;

        Set<String> possibleSubstrings = possibleSubstrings(longer);
        double g = commonSubstrings(shorter, possibleSubstrings);
        double d = possibleSubstrings.size();
        return g / d;
    }

    public double commonSubstrings(String shorter, Set<String> possibleSubstrings) {
        double sum = 0;

        for (int i = 0; i < shorter.length() - n + 1; i++) {
            if (possibleSubstrings.contains(shorter.substring(i, i + n))) {
                sum++;
            }
        }
        return sum;
    }

    public Set<String> possibleSubstrings(String word) {
        Set<String> substrings = new HashSet<>();
        for (int i = 0; i < word.length() - n + 1; i++) {
            substrings.add(word.substring(i, i + n));
        }
        return substrings;
    }
}
