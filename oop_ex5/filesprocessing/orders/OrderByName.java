package filesprocessing.orders;

import java.io.File;

/**
 * this class override the java compare method by using jave compareTo method, will compare
 * the by their name, defined as the DEFAULT orders
 */
public class OrderByName extends Order {
    /**
     * the method override the compare method in Comparator, compare by pre defined condition-NAME
     *
     * @param lhs the first argument
     * @param rhs the second argument
     * @return 1 if the left arg is bigger, -1 if the right arg is bigger,0 if equal
     */
    @Override
    public int compare(File lhs, File rhs) {
        return lhs.getAbsolutePath().compareTo(rhs.getAbsolutePath());
    }
}
