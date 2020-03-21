package filesprocessing;

/**
 * Type1 exception, will handle all the little problems in the program- those problems
 * wont result the program to exit
 */
public class TypeOneException extends Exception {

    private static final String WARNING_MSG = "Warning in line ";

    /**
     * the method responsible on printing the warning
     *
     * @param line the line which the program has raised a problem
     */
    void msg(int line) {
        System.err.println(WARNING_MSG + line);
    }
}
