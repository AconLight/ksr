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

    public static int mode = 1;
    // 0 - by datasets
    // 1 - not by datasets

    public static int[] freqN = {5, 10, 15};

    public static float[][] avgWeights = {
            {1f, 1f, 1f},
            {0.2f, 0.6f, 1f},
            {1f, 0.6f, 0.2f},
            {0.2f, 1f, 0,2f},
            {1f, 0.2f, 1f}
    };

    public static int dataSetsRange = 3;

    public static List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public static IWordSimilarity[] wordSimilarities = {
            new NGram(1),
            new GeneralizedNGramWithLimits(1, 2)
    };

    public static IMetric[] metrics = {
            new ManhattanMetric(),
            new EuclideanMetric(),
            new ChebyshevMetric()
    };

    public static int[] k = { 3 };

    public static FeatureExtractorsConfig[] featureExtractorsConfig = {
            FeatureExtractorsConfig.fec2()
    };
}
