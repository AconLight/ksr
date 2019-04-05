package runner;

import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;
import word.similarity.NGram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunnerConfig {
    public static int dataSetsRange = 10;
    public static FeatureExtractorsConfig[] featureExtractorsConfig = {
            FeatureExtractorsConfig.fec1()
    };

    public static List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public static IWordSimilarity[] wordSimilarities = {
            new NGram(1),
    };

    public static IMetric[] metrics = {
            new ManhattanMetric(),
            new EuclideanMetric(),
            new ChebyshevMetric()
    };

    public static int[] k = { 3 };
}
