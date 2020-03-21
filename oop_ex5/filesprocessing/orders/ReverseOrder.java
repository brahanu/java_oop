package filesprocessing.orders;

import java.io.File;

/**
 * a decorator for the orders, will reverse the orders of the given orders
 */
class ReverseOrder extends Order {

    private static final int REVERSE_FACTOR = -1;
    private Order sequence;

    /**
     * constructor for the decorator class, will "negate" the given orders
     *
     * @param sequence the orders that the user want to reverse
     */
    ReverseOrder(Order sequence) {
        this.sequence = sequence;
    }

    /**
     * the method override the compare method in Comparator, compare by pre defined condition
     *
     * @param lhs the first argument
     * @param rhs the second argument
     * @return -1 if the left arg is bigger, 1 if the right arg is bigger,0 if equal
     */
    @Override
    public int compare(File lhs, File rhs) {
        return sequence.compare(lhs, rhs) * REVERSE_FACTOR;
    }
}
