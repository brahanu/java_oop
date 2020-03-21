package filesprocessing.orders;

import java.io.File;

/**
 * this class override the java compare method will compare the by their type
 */
class OrderByType extends Order {

    private static final int EQUAL_VAL = 0;

    /**
     * the method override the compare method in Comparator, compare by pre defined condition-TYPE
     *
     * @param lhs the first argument
     * @param rhs the second argument
     * @return 1 if the left arg is bigger, -1 if the right arg is bigger,0 if equal
     */
    @Override
    public int compare(File lhs, File rhs) {

        int compareVal = getFileType(lhs).compareTo(getFileType(rhs));
        if (compareVal == EQUAL_VAL) {
            return lhs.getName().compareTo(rhs.getName());
        } else {
            return compareVal;
        }

    }

    /**
     * helper method for the comapre by type class, will get us the file type
     *
     * @param file the file we want to get its type
     * @return a string representing the file type
     */
    private String getFileType(File file) {
        return file.getName().substring(file.getName().lastIndexOf(FILE_NAME_SEPARATOR) + 1);
    }
}