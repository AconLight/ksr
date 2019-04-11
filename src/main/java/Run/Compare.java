package Run;

import config.Config;
import runner.Runner;
import runner.RunnerConfig;

public class Compare {
    public static void main(String[] args) {
        Runner r = new Runner();
        Config.tag = "TOPICS";
        Config.setLabels(Config.acceptedTopics);
        r.compare("topics");

        r = new Runner();
        Config.tag = "PLACES";
        Config.setLabels(Config.acceptedPlaces);
        r.compare("places");

        r = new Runner();
        RunnerConfig.isMail = true;
        Config.setLabels(Config.acceptedMail);
        r.compare("spam");
    }
}
