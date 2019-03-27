package dataOperations.dataLoading;

import classifiedObjects.ClassifiedObject;
import dataOperations.FeatureVector;
import dataOperations.dataLoading.featureExtractors.MainFeatureExtractor;
import dataOperations.dataLoading.fileReaders.IFileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class DataLoader<T extends ClassifiedObject> {
    public List<T> loadObjects(Path directory, IFileReader<T> reader) {
        List<T> objects = new ArrayList<>();

        File[] directoryListing = directory.toFile().listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                objects.addAll(reader.read(f));
            }
        } else {
            throw new IllegalArgumentException();
        }

        return objects;
    }

    public List<FeatureVector> loadFeatureVectors(String directory, IFileReader<T> reader, MainFeatureExtractor<T> extractor) {
        List<FeatureVector> featureVectors = new ArrayList<>();

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                List<T> objects = reader.read(f);
                for (T o : objects) {
                    featureVectors.add(extractor.extract(o));
                }
            }
        } else {
            throw new IllegalArgumentException();
        }


        return featureVectors;

    }
}
