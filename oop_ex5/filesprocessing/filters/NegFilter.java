package filesprocessing.filters;

import java.io.File;

/**
 * a decorator class, will get a given condition and negate its output
 */
class NegFilter implements Filter {

    private Filter condition;

    /**
     * constructor for the NegFilter class, will negate the given condition
     *
     * @param condition the condition which we want to negate
     */
    NegFilter(Filter condition) {
        this.condition = condition;
    }

    /**
     * @param file the file we want to check if its hold the condition
     * @return true if the file hold the condition false otherwise
     */
    @Override
    public boolean filter(File file) {
        return !(this.condition.filter(file));
    }
}
