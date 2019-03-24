package runner;

import config.Config;

public class Configurations {
    public Config basicConfig() {
        return Config.builder()
                .KNN_K(1)
                //.METRIC(null)
                .build();
    }
}
