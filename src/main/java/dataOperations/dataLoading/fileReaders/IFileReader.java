package dataOperations.dataLoading.fileReaders;

import java.io.File;
import java.util.List;

public interface IFileReader<T> {
    List<T> read(File file);
}
