package dataOperations.dataLoading;

import dataOperations.classifiedObjects.ClassifiedObject;
import dataOperations.FeatureVector;
import dataOperations.dataLoading.featureExtractors.MainFeatureExtractor;
import dataOperations.dataLoading.fileReaders.IFileReader;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class DataLoader {
    public static List<ClassifiedObject> loadObjects(String directory, IFileReader reader) {
        List<ClassifiedObject> objects = new ArrayList<ClassifiedObject>();

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                objects.addAll(reader.read(f));
            }
        } else {
            throw new IllegalArgumentException();
        }

        return objects;
    }

    public static List<FeatureVector> loadFeatureVectors(String directory, IFileReader reader, MainFeatureExtractor extractor) {
        List<FeatureVector> featureVectors = new ArrayList<>();

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                List<ClassifiedObject> objects = reader.read(f);
                for (ClassifiedObject o : objects) {
                    featureVectors.add(extractor.extract(o));
                }
            }
        } else {
            throw new IllegalArgumentException();
        }


        return featureVectors;

    }
}
