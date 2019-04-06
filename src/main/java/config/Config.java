package config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    private static final String[] acceptedPlaces = {"west-germany", "usa", "france", "uk", "canada", "japan"};
    public static final List<String> placesLabels = new ArrayList<>(Arrays.asList(acceptedPlaces));

    public static final Path rootPath = Paths.get(".");
    public static final Path learningSetPath = rootPath.resolve("data").resolve("learningSet");
    public static final Path testSetPath = rootPath.resolve("data").resolve("testSet");
    public static final Path initTestSetPath = rootPath.resolve("data").resolve("initTestSet");
    public static final Path resultsPath = rootPath.resolve("data").resolve("results");
    public static final Path runnerresultsPathDir = rootPath.resolve("data").resolve("runnerresults");
    public static Path runnerresultsPath(String name) {
        return rootPath.resolve("data").resolve("runnerresults").resolve(name);
    }
    public static final Path bestrunnerresultsPathDir = rootPath.resolve("data").resolve("bestrunnerresults");
    public static Path bestrunnerresultsPath(String name) {
        return rootPath.resolve("data").resolve("bestrunnerresults").resolve(name);
    }
    public static final Path allSetPath = rootPath.resolve("data").resolve("dataSet");
}
