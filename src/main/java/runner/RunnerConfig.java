package runner;

import config.Config;
import metrics.ChebyshevMetric;
import metrics.EuclideanMetric;
import metrics.IMetric;
import metrics.ManhattanMetric;
import utils.ExtractionMethod;
import word.similarity.GeneralizedNGramWithLimits;
import word.similarity.IWordSimilarity;
import word.similarity.NGram;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunnerConfig {

    public static void set() {
        dataDiv=BestRunnerConfig.dataDiv;
        dataSize=BestRunnerConfig.dataSize;
        freqN=BestRunnerConfig.freqN;
        avgWeights=BestRunnerConfig.avgWeights;
        wordSimilarities=BestRunnerConfig.wordSimilarities;
        metrics=BestRunnerConfig.metrics;
        extractionMethods=BestRunnerConfig.extractionMethods;
        featureExtractorsConfig=BestRunnerConfig.featureExtractorsConfig;
        mode=BestRunnerConfig.mode;
        k=BestRunnerConfig.k;
        FeatureExtractorsConfig[] fec = {
                FeatureExtractorsConfig.fec3()
        };
        featureExtractorsConfig = fec;
        dataSetsRange = BestRunnerConfig.dataSetsRange;
    }

    public static String path = "";

    public static void setFromConfig() {


    }

    public static float[] dataDiv = {
            0.6f
    };

    public static int[] dataSize = {
            100
    };

    public static Boolean isMail = false;

    public static int mode = 1;
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
            //new EuclideanMetric(),
            new ChebyshevMetric()
    };

    public static ExtractionMethod[] extractionMethods = {
            ExtractionMethod.OVERALL_WORD_OCCURRENCES
    };

    public static int[] k = {9, 10, 11};

    public static FeatureExtractorsConfig[] featureExtractorsConfig = {
            FeatureExtractorsConfig.fec3()
    };
}
