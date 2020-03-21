package filesprocessing;

/**
 * the class extends the TypeTwo exception handle invalid usage exceptions
 */
class InvalidUsageException extends TypeTwoExceptions {
    private static final String msg = "Invalid usage Exception\n";

    /**
     * constructor for the class, hold the needed msg for the exception
     */
    InvalidUsageException() {
        super(msg);
    }
}
