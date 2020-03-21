package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the filters "greater_than"
 */
class GreaterThanFilter extends FilterBySize {

    private double lowerThreshold;

    /**
     * a constructor for the greater than filters
     *
     * @param lowerThreshold the lower bound that all the file need to uphold
     */
    GreaterThanFilter(double lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getSize(file) > lowerThreshold;
    }
}
