package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Objects;

import static config.Config.resultsPath;

class StatWriter {
    static void write(TestResults testResults) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(resultsPath.toFile(), true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(writer).println(testResults.toString());
        writer.checkError();
    }
}
