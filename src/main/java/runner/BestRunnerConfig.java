package runner;

import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;

import java.util.List;
import java.util.Map;

public class BestRunnerConfig {
    public static void setFromConfig() {


    }

    public static float[] dataDiv = {
            0.6f
    };

    public static int[] dataSize = {
            400
    };

    public static Boolean isMail = false;

    public static int mode = 0;
    // 0 - by datasets
    // 1 - not by datasets

    public static int[] freqN = {7};

    public static float[][] avgWeights = {
            {1f, 0.6f, 0.2f},
            //{0.2f, 0.6f, 1f},
            //{1f, 1f, 1f}
    };

    public static int dataSetsRange = 1;

    public static List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public static IWordSimilarity[] wordSimilarities = {
            new GeneralizedNGramWithLimits(2, 3),

    };

    public static IMetric[] metrics = {
            //new ManhattanMetric(),
            new EuclideanMetric(),
            //new ChebyshevMetric()
    };

    public static ExtractionMethod[] extractionMethods = {
            ExtractionMethod.TFIDF
    };

    public static int[] k = {1};

    public static FeatureExtractorsConfig[] featureExtractorsConfig = {
            FeatureExtractorsConfig.fec3()
    };
}
