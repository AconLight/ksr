package runner;

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
            FeatureExtractorsConfig.fec1(),
            FeatureExtractorsConfig.fec2()
    };

    public static List<Map<ExtractionMethod, Map<String, List<String>>>> keyWordsList;

    public static IWordSimilarity[] wordSimilarities = {
            new NGram(1),
            new GeneralizedNGramWithLimits(1, 2),
            new GeneralizedNGramWithLimits(2, 2)
    };
}
