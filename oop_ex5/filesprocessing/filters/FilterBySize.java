package filesprocessing.filters;

import java.io.File;

/**
 * an abstract class that hold method and variables relevant to all the filters
 * that need the file size
 */
abstract class FilterBySize implements Filter {

    private static final double KILOBYTE_FACTOR = 1024;

    /**
     * helper method to all the children class,will return the file size by KB
     *
     * @param file the file we want to check its size by KB
     * @return the file size in KB
     */
    double getSize(File file) {
        return file.length() / KILOBYTE_FACTOR;
    }


}
