package filesprocessing.filters;

import java.io.File;

/**
 * The class represents the filters "hidden"
 */
class Hidden implements Filter {
    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return file.isHidden();
    }
}
