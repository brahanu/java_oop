package filesprocessing.filters;

import java.io.File;

/**
 * Interface master class for the filters system
 */
public interface Filter {
    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    boolean filter(File file);
}
