package junit.tutorial.exam.processor.file;

import junit.tutorial.exam.processor.file.exception.FileProcessingException;

import java.io.File;

public class RecursiveFileProcessor implements FileProcessor {

    private FileProcessor internalProcessor;

    public RecursiveFileProcessor(FileProcessor internalProcessor) {
        this.internalProcessor = internalProcessor;
    }

    @Override
    public void process(File file) throws FileProcessingException {
        if (file.isDirectory())
            processRecursively(file.listFiles());
        else
            processRecursively(new File[]{file});
    }

    public void processRecursively(File[] files) throws FileProcessingException {
        for (File file : files) {
            if (file.isDirectory()) {
                processRecursively(file.listFiles());
            } else {
                internalProcessor.process(file);
            }
        }
    }

}