package dataOperations.dataLoading.fileReaders;

import dataOperations.classifiedObjects.ClassifiedObject;

import java.io.File;
import java.util.List;

public interface IFileReader {
    List<ClassifiedObject> read(File file);
}
