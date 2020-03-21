package filesprocessing.orders;

import filesprocessing.TypeOneException;

/**
 * a factory class for the orders class
 */
public class OrderFactory {

    private static final String SEPARATOR = "#";
    private static final String SIZE = "size", TYPE = "type", ABSOLUTE = "abs", REVERSE = "REVERSE";
    private static final int ORDER_VAL = 0;

    /**
     * the method responsible on creating all the needed orders according to the user input
     *
     * @param input the user input
     * @return an orders according to the user input
     * @throws TypeOneException if there is any problem with the user we throw an exception
     */
    public static Order orderFactory(String input) throws TypeOneException {
        String[] orderArgs = parseArguments(input);
        boolean isReverse = false;
        if (input.substring(input.lastIndexOf(SEPARATOR) + 1).equals(REVERSE)) {
            isReverse = true;
        }
        Order result;
        switch (orderArgs[ORDER_VAL]) {
            case SIZE:
                result = new OrderBySize();
                return reverseHandler(isReverse, result);
            case TYPE:
                result = new OrderByType();
                return reverseHandler(isReverse, result);
            case ABSOLUTE:
                result = new OrderByName();
                return reverseHandler(isReverse, result);
            default:
                throw new TypeOneException();
        }
    }

    /**
     * parse the arguments from the given string into an array
     *
     * @param orderName the given string input
     * @return an string array which each cell represent argument value
     */
    private static String[] parseArguments(String orderName) {
        return orderName.split(SEPARATOR);
    }

    /**
     * handle the reverse decorator
     *
     * @param isReverse true if there were REVERSE in the right place in the user input,
     *                  otherwise false
     * @param order     the wanted orders
     * @return an orders according to if we need to reverse the file
     */
    private static Order reverseHandler(boolean isReverse, Order order) {
        if (isReverse) {
            return new ReverseOrder(order);
        } else {
            return order;
        }
    }
}