package runner;

public class RunnerConfig {
    public static int dataSetsRange = 10;
    public static FeatureExtractorsConfig[] featureExtractorsConfig = {
            FeatureExtractorsConfig.fec1(),
            FeatureExtractorsConfig.fec2()
    };
}
