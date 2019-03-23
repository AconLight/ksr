package data.dataLoading.fileReaders;

import data.ClassifiedObject;

import java.io.File;

public interface IFileReader {
    ClassifiedObject read(File file);
}
