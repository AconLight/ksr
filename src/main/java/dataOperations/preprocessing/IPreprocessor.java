package dataOperations.preprocessing;

public interface IPreprocessor<T> {
    T process(T t);
}
