package filesprocessing;

/**
 * the class represent the BadCommandFileException. represent all the problem with the commandFile
 */
public class BadCommandFileException extends TypeTwoExceptions {

    private static final String msg = "The Command File have a bad format \n";

    /**
     * constructor for the class, hold the needed msg for the exception
     */
    public BadCommandFileException() {
        super(msg);
    }

}
