package filesprocessing;

/**
 * will catch A bad sub-section name (i.e., not FILTER/ORDER). Sub-section names are case-sensitive
 */
class BadSubSectionException extends TypeTwoExceptions {
    private static final String msg = "Problem with subsections in CommandFile\n";

    /**
     * constructor for the class, hold the needed msg for the exception
     */
    BadSubSectionException() {
        super(msg);
    }

}
