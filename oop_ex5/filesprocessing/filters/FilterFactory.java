package filesprocessing.filters;

// same as take2 in the ex5 suggested design

import filesprocessing.TypeOneException;

/**
 * Factory for the filters, will create a filters according to the user input
 */
public class FilterFactory {

    private static final String NEGATE = "NOT", ALL = "all", HIDDEN = "hidden",
            EXECUTABLE = "executable", WRITABLE = "writable",
            SUFFIX = "suffix", PREFIX = "prefix", CONTAINS = "contains", FILE_FILTER = "file",
            SMALLER_THAN = "smaller_than", BETWEEN = "between", GREATER_THAN = "greater_than",
            YES = "YES", NO = "NO";
    private static final String SEPARATOR = "#";
    private static final int FILTER_VAL = 0;
    private static final int FIRST_ARG = 1;
    private static final int SECOND_ARG = 2;
    private static final int MAX_ARG = 4;
    private static final int NEG_DEFAULT_ARG_NUM = 3;
    private static final int MIN_ARG_VAL = 0;
    private static final int DEFAULT_ARG_NUM = 2;

    /**
     * the method will create filters according to the given arguments
     *
     * @param input a string representing the wanted filters combination
     * @return the wanted filters
     * @throws TypeOneException if there is a problem with the given string will throw
     *                          an typeOneException as defined in the pdf
     */
    public static Filter filterFactory(String input) throws TypeOneException {
        String[] args = validateArguments(input);
        boolean isNot = false;
        if (input.substring(input.lastIndexOf(SEPARATOR) + 1).equals(NEGATE)) {
            isNot = true;
        }
        if ((args[FILTER_VAL].equals(WRITABLE) || args[FILTER_VAL].equals(EXECUTABLE) ||
                args[FILTER_VAL].equals(HIDDEN)) && (args[FIRST_ARG].equals(NO))) {
            isNot = true;
        }
        Filter result;
        switch (args[FILTER_VAL]) {
            case ALL:
                result = new All();
                return notFilterHandler(isNot, result);
            case HIDDEN:
                result = new Hidden();
                return notFilterHandler(isNot, result);
            case EXECUTABLE:
                result = new Executable();
                return notFilterHandler(isNot, result);
            case WRITABLE:
                result = new Writable();
                return notFilterHandler(isNot, result);
            case SUFFIX:
                result = new Suffix(args[FIRST_ARG]);
                return notFilterHandler(isNot, result);
            case PREFIX:
                result = new Prefix(args[FIRST_ARG]);
                return notFilterHandler(isNot, result);
            case CONTAINS:
                result = new Contains(args[FIRST_ARG]);
                return notFilterHandler(isNot, result);
            case FILE_FILTER:
                result = new FileName(args[FIRST_ARG]);
                return notFilterHandler(isNot, result);
            case SMALLER_THAN:
                result = new SmallerThanFilter(getDouble(args[FIRST_ARG]));
                return notFilterHandler(isNot, result);
            case BETWEEN:
                result = new BetweenFilter(getDouble(args[FIRST_ARG]), getDouble(args[SECOND_ARG]));
                return notFilterHandler(isNot, result);
            case GREATER_THAN:
                result = new GreaterThanFilter(getDouble(args[FIRST_ARG]));
                return notFilterHandler(isNot, result);
            default:
                throw new TypeOneException();
        }
    }

    /**
     * the method will modify the given string into a manageable array
     *
     * @param filterString a string representing the wanted filters
     * @return string list that each place describe an argument for the filters
     */
    private static String[] getArguments(String filterString) {
        return filterString.split(SEPARATOR);
    }

    private static double getDouble(String value) throws TypeOneException {
        double parsedDouble = Double.parseDouble(value);
        if (parsedDouble < MIN_ARG_VAL) {
            throw new TypeOneException();
        } else {
            return Double.parseDouble(value);
        }
    }

    /**
     * the method will validate the given arguments as defined in the pdf
     *
     * @param input the given string from the user
     * @return will return a validated string array that represents the wanted filters
     * @throws TypeOneException if the string is not valid will throw a typeOneException
     */
    private static String[] validateArguments(String input) throws TypeOneException {
        String[] args = getArguments(input);
        if (args.length > MAX_ARG) {
            throw new TypeOneException();
        } else if (args[FILTER_VAL].equals(BETWEEN) && args.length > DEFAULT_ARG_NUM &&
                getDouble(args[FIRST_ARG]) > getDouble(args[SECOND_ARG])) {
            throw new TypeOneException();
        } else if (!args[FILTER_VAL].equals(BETWEEN) && args.length == NEG_DEFAULT_ARG_NUM &&
                !input.substring(input.lastIndexOf(SEPARATOR) + FIRST_ARG).equals(NEGATE)) {
            throw new TypeOneException();
        } else if (args[FILTER_VAL].equals(BETWEEN) && args.length == MAX_ARG &&
                !input.substring(input.lastIndexOf(SEPARATOR) + FIRST_ARG).equals(NEGATE)) {
            throw new TypeOneException();
        } else if ((args[FILTER_VAL].equals(SMALLER_THAN) || args[FILTER_VAL].equals(GREATER_THAN))
                && args.length > NEG_DEFAULT_ARG_NUM) {
            throw new TypeOneException();
        } else if ((args[FILTER_VAL].equals((WRITABLE)) || args[FILTER_VAL].equals((EXECUTABLE))
                || args[FILTER_VAL].equals((HIDDEN))) && (!args[FIRST_ARG].equals(YES) &&
                !args[FIRST_ARG].equals(NO))) {
            throw new TypeOneException();
        }
        return args;
    }

    /**
     * will mange the negate decorator
     *
     * @param isNot  a boolean value will be true if the user input include NOT or NO
     * @param filter the wanted filters
     * @return a negated filters if needed
     */
    private static Filter notFilterHandler(boolean isNot, Filter filter) {
        if (isNot) {
            return new NegFilter(filter);
        } else {
            return filter;
        }
    }
}