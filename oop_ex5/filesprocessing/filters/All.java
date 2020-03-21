
package filesprocessing.filters;

import java.io.File;

/**
 * the class define the all filters, meaning we wont filters any files - defined as the
 * DEFAULT filters for the program
 */
public class All implements Filter {
    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return true;
    }
}