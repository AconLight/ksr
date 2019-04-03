package runner;

import utils.ExtractionMethod;

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
}
