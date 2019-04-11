package Run;

import config.Config;
import runner.Runner;

public class FindBest {
    public static void main(String[] args) {
        Runner r = new Runner();
        Config.tag = "PLACES";
        Config.setLabels(Config.acceptedPlaces);
        r.findBest();
    }
}
