package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the filters "file"
 */
class FileName extends FilterByValue {

    private String value;

    /**
     * a  constructor for the Filter by file class
     *
     * @param file the file which we filters accordingly
     */
    FileName(String file) {
        this.value = file;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getFileName(file).equals(value);
    }
}
