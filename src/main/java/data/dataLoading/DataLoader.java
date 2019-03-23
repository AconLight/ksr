package data.dataLoading;

import data.ClassifiedObject;
import data.Feature;
import data.FeatureVector;
import data.dataLoading.fileReaders.IFileReader;

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
                objects.add(reader.read(f));
            }
        } else {
            throw new IllegalArgumentException();
        }

        return objects;
    }

    public static List<Feature> loadFeatureVectors(String directory, IFileReader reader, FeatureExtractor extractor) {
        List<Feature> featureVectors = new ArrayList<Feature>();

        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                ClassifiedObject object = reader.read(f);
                featureVectors.add(extractor.extract(object));
            }
        } else {
            throw new IllegalArgumentException();
        }

        return featureVectors;

    }
}
