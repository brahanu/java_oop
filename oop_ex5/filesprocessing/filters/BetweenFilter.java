package filesprocessing.filters;

import java.io.File;

/**
 * the class represent the between filters, will filters all teh file that their size is not in the
 * given range
 */
class BetweenFilter extends FilterBySize {
    private double upperThreshold;
    private double lowerThreshold;

    /**
     * constructor for the between filters, will filters all the file that not in the given range
     *
     * @param lowerThreshold the lower bound for the file size given by the user
     * @param upperThreshold the upper bound for the file size given by the user
     */
    BetweenFilter(double lowerThreshold, double upperThreshold) {
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return getSize(file) >= this.lowerThreshold
                && getSize(file) <= this.upperThreshold;
    }
}
