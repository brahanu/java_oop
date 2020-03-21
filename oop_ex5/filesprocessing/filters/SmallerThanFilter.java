package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the filters "smaller_than"
 */
class SmallerThanFilter extends FilterBySize {

    private double upperThreshold;

    /**
     * a constructor for the greater than filters
     *
     * @param upperThreshold the upper bound that all the files need to uphold
     */
    SmallerThanFilter(double upperThreshold) {
        this.upperThreshold = upperThreshold;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getSize(file) < this.upperThreshold;
    }
}
