package filesprocessing.filters;

import java.io.File;

/**
 * the class will filters the files by the given condition if they contain a certain value
 */
class Contains extends FilterByValue {
    private String value;

    /**
     * constructor for the contains filters, will get a string value which the files will be filtered
     * out if they not contain it
     */
    Contains(String value) {
        this.value = value;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getFileName(file).contains(value);
    }
}
