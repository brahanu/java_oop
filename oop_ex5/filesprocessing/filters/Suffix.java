package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the filters "suffix"
 */
class Suffix extends FilterByValue {

    private String value;

    /**
     * a constructor for the suffix filters
     *
     * @param value the suffix that we want to filters upon
     */
    Suffix(String value) {
        this.value = value;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getFileName(file).endsWith(this.value);
    }
}
