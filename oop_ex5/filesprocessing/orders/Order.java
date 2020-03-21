package filesprocessing.orders;

import java.io.File;
import java.util.Comparator;

/**
 * an abstract class that implements the orders for the project
 */
public abstract class Order implements Comparator<File> {

    static final String FILE_NAME_SEPARATOR = ".";

    /**
     * the method override the compare method in Comparator, compare by pre defined condition
     *
     * @param lhs the first argument
     * @param rhs the second argument
     * @return 1 if the left arg is bigger, -1 if the right arg is bigger,0 if equal
     */
    public abstract int compare(File lhs, File rhs);


}
