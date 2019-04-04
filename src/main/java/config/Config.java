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
}
