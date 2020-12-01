package junit.tutorial.exam.processor.file;

import junit.tutorial.exam.processor.file.exception.FileProcessingException;
import lombok.SneakyThrows;

import java.io.File;
import java.util.function.Consumer;

public interface FileProcessor extends Consumer<File> {

    void process(File file) throws FileProcessingException;

    @SneakyThrows
    @Override
    default void accept(File file) {
        process(file);
    }
}
