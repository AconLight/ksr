package dataOperations.textStatistics;

import java.util.List;

class TFIDFCalculator {
    private double tf(List<String> doc, String term) {
        int result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return (double) result / doc.size();
    }

    private double idf(List<List<String>> docs, String term) {
        int n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(1 + docs.size() / (float) n);
    }

    double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }
}
