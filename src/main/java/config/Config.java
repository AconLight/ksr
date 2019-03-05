package config;

import lombok.Builder;
import metrics.IMetric;
import word.similarity.IWordSimilarity;

@Builder
public class Config {
    private double KNN_K; // const K in KNN algorithm
    private IMetric OCCURRENCE_METRIC; // to implement the importance of occurrence number (for instance big number may be bad as professor Niewiadomski sad)
    private IMetric WORD_SIMILARITY_METRIC; // to implement soft or hard matching of the given key words
    private IMetric VECTOR_DISTANCE_METRIC; // between 
    private IWordSimilarity WORD_SIMILARITY;
}
