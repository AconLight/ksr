package config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    public static  String tag = "PLACES";
    public static String[] acceptedPlaces = {"west-germany", "france", "uk", "canada", "japan"};
    public static final String[] acceptedTopics = {"acq", "money-supply", "money-fx ", "crude", "copper"};
    public static final String[] acceptedMail = {"mail", "spam"};
    public static List<String> placesLabels = new ArrayList<>(Arrays.asList(acceptedPlaces));

    public static void setLabels(String[] labels) {
        placesLabels = new ArrayList<>(Arrays.asList(labels));
    }

    public static final Path rootPath = Paths.get(".");
    public static final Path learningSetPath = rootPath.resolve("data").resolve("learningSet");
    public static final Path testSetPath = rootPath.resolve("data").resolve("testSet");
    public static final Path initTestSetPath = rootPath.resolve("data").resolve("initTestSet");
    public static final Path resultsPath = rootPath.resolve("data").resolve("results");
    public static final Path runnerresultsPathDir = rootPath.resolve("data").resolve("runnerresults");
    public static Path getRunnerresultsPath(String name1, String name2) {
        return rootPath.resolve("data").resolve("runnerresults").resolve(name1).resolve(name2).resolve("runnerresults");
    }
    public static Path runnerresultsPath(String name) {
        return rootPath.resolve("data").resolve("runnerresults").resolve(name);
    }
    public static final Path bestrunnerresultsPathDir = rootPath.resolve("data").resolve("bestrunnerresults");
    public static Path bestrunnerresultsPath(String name) {
        return rootPath.resolve("data").resolve("bestrunnerresults").resolve(name);
    }
    public static final Path allSetPath = rootPath.resolve("data").resolve("dataSet");
    public static final Path mailSets = rootPath.resolve("data").resolve("dataSetMail").resolve("mail");
    public static final Path spamSets = rootPath.resolve("data").resolve("dataSetMail").resolve("spam");
    public static final Path runnerConfig = rootPath.resolve("data").resolve("runnerConfig");
}
