package word.similarity;

public interface IWordSimilarity {
    Double measure(String s1, String s2);
    String getParams();
}
