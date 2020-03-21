package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the filters "prefix"
 */
class Prefix extends FilterByValue {

    private String value;

    Prefix(String value) {
        this.value = value;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getFileName(file).startsWith(this.value);
    }
}
