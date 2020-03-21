package filesprocessing.orders;

import java.io.File;

/**
 * this class override the java compare method by using jave compareTo method, will compare
 * the by their size,
 */
class OrderBySize extends Order {

    private static final int FIRST_ARG = 1;
    private static final int SECOND_ARG = -1;

    /**
     * the method override the compare method in Comparator, compare by pre defined condition-SIZE
     *
     * @param lhs the first argument
     * @param rhs the second argument
     * @return 1 if the left arg is bigger, -1 if the right arg is bigger,0 if equal
     */
    @Override
    public int compare(File lhs, File rhs) {
        if (lhs.length() > rhs.length()) {
            return FIRST_ARG;
        } else if (lhs.length() < rhs.length())
            return SECOND_ARG;
        else {
            return (lhs.getAbsolutePath().compareTo(rhs.getAbsolutePath()));

        }
    }
}
