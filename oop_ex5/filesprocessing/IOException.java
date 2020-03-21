package filesprocessing;

/**
 * the class extends the TypeTwo exception handle IO exceptions
 */
class IOException extends TypeTwoExceptions {

    private static final String msg = "Problem with program IO\n";

    /**
     * constructor for the class, hold the needed msg for the exception
     */
    IOException() {
        super(msg);
    }
}
