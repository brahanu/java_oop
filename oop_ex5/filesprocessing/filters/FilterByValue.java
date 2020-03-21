package filesprocessing.filters;

import java.io.File;

/**
 * an abstract class that hold all the methods that relevant to the filters by the string Value
 */
abstract class FilterByValue implements Filter {
    /**
     * helper method to all children class, return the file name
     *
     * @param file the file we want to check its name
     * @return the file name
     */
    String getFileName(File file) {
        return file.getName();
    }
}
