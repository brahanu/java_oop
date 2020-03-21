package filesprocessing;

/**
 * an abstract class for all the type2 exceptions
 */
abstract class TypeTwoExceptions extends Exception {

    private static final String ERROR = "ERROR: ";

    /**
     * constructor for the class, get the needed msg so we will be able to print it
     *
     * @param msg the needed msg, according to the exception
     */
    TypeTwoExceptions(String msg) {
        super(ERROR + msg);
    }
}
