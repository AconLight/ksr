package config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    public static final Path rootPath = Paths.get(".");
    public static final Path learningSetPath = rootPath.resolve("data").resolve("learningSet");
}
