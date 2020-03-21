import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * has a main method that measures the run-times requested
 * in the "Performance Analysis" section.
 */
public class SimpleSetPerformanceAnalyzer {

    private static final String DATA1 = "data1.txt";
    private static final String DATA2 = "data2.txt";
    private static final int NS_TO_MS_FACTOR = 1000000;
    private static final int WARM_UP_SETS = 70000;
    private static final int WARM_UP_LLS = 7000;
    private static final String SEARCH_VAL_HI = "hi";
    private static final String SEARCH_VAL_NUM = "23";
    private static final String SEARCH_VAL_NEGATIVE_NUM = "-13170890158";
    private static final int TEST_ADD_ALL_DATA1 = 1;
    private static final int TEST_ADD_ALL_DATA2 = 2;
    private static final int TEST_SEARCH_HI_DATA1 = 3;
    private static final int TEST_SEARCH_NEGATIVE_NUM_DATA1 = 4;
    private static final int TEST_SEARCH_NUM_DATA2 = 5;
    private static final int TEST_SEARCH_HI_DATA2 = 6;
    private static final int TEST_ALL = 7;
    private static SimpleSet[] simpleSets;
    private static final String SET_ONE_OPEN_HASH = "OpenHashSet";
    private static final String SET_TWO_CLOSED_HASH = "ClosedHashSet";
    private static final String SET_THREE_TREE = "TreeSet";
    private static final String SET_FOUR_LLS = "LinkedList";
    private static final String SET_FIVE_HASH = "HashSet";

    private static final String[] simpleSetsTypes = {SET_ONE_OPEN_HASH, SET_TWO_CLOSED_HASH,
            SET_THREE_TREE, SET_FOUR_LLS, SET_FIVE_HASH};
    private static final String[] dataOneArray = Ex4Utils.file2array(DATA1);
    private static final String[] dataTwoArray = Ex4Utils.file2array(DATA2);

    /**
     * Init an array of SimpleSets, with all the dataSets requested in the "Performance Analysis"
     */
    private static void init() {
        simpleSets = new SimpleSet[5];
        simpleSets[0] = new OpenHashSet();
        simpleSets[1] = new ClosedHashSet();
        simpleSets[2] = new CollectionFacadeSet(new TreeSet<String>());
        simpleSets[3] = new CollectionFacadeSet(new LinkedList<String>());
        simpleSets[4] = new CollectionFacadeSet(new HashSet<String>());

    }

    /**
     * helper method for the contain Tests, add all the data into the SimpleSet
     *
     * @param data the data we want our SimpleSet will hold
     */
    private static void addData(String[] data) {
        for (SimpleSet set : simpleSets) {
            for (String item : data) {
                set.add(item);
            }
        }
    }

    /**
     * test how much time it takes to add all the data into the SimpleSet
     *
     * @param data the data we want our SimpleSet will hold
     */
    private static void checkAddAllData(String[] data) { // in ms
        init();
        for (int i = 0; i < simpleSetsTypes.length; i++) {
            System.out.println("Start adding to " + simpleSetsTypes[i]);
            long startTime = System.nanoTime();
            for (String item : data) {
                simpleSets[i].add(item);
            }
            long endTime = System.nanoTime();
            long total = (endTime - startTime) / NS_TO_MS_FACTOR;
            System.out.println("Adding all the given data to " + simpleSetsTypes[i] + " took " + total);
        }

    }

    /**
     * test how much time it takes to find a value in the SimpleSet
     *
     * @param data      the data we want our SimpleSet to hold
     * @param searchVal the value we want to check how much time it will take to find in the
     *                  SimpleSet
     */
    private static void checkContain(String[] data, String searchVal, boolean isTestAll) {
        if (isTestAll) {
            checkContainSetHelper(searchVal);
        } else {
            System.out.println("Start Initialization");
            init();
            addData(data);
            System.out.println("Finished Initialization");
            checkContainSetHelper(searchVal);
        }
    }

    /**
     * the method reposnsible to mange the warm stage for the contains
     *
     * @param value the string we are looking for in teh
     */
    private static void checkContainSetHelper(String value) {
        for (int i = 0; i < 5; i++) {
            if (simpleSets[i] instanceof LinkedList) {
                checkContainLoopHelper(value, WARM_UP_LLS, i);
            } else {
                checkContainLoopHelper(value, WARM_UP_SETS, i);
            }
        }
    }

    /**
     * a helper method for the checkContain method,
     *
     * @param value  the value we want to search for
     * @param warmUp how much warmUp rounds is needed for the data set
     * @param set    the SimpleSet we to check
     */
    private static void checkContainLoopHelper(String value, int warmUp, int set) {
        if (!(simpleSets[set] instanceof LinkedList)) {
            for (int j = 0; j < warmUp; j++) {
                simpleSets[set].contains(value);
            }
        }
        long startTime = System.nanoTime();
        for (int j = 0; j < warmUp; j++) {
            simpleSets[set].contains(value);
        }
        long endTime = System.nanoTime();
        long total = (endTime - startTime) / warmUp;
        System.out.println("For the item " + value +
                " total time of contain for " + simpleSetsTypes[set] + " " + total);
    }

    /**
     * manage the testing process,activate the needed test according userInput he will get in the
     * main method
     *
     * @param testNumber an integer that indicate which test to activate
     */
    private static void chooseTest(int testNumber) {
        if (testNumber == TEST_ADD_ALL_DATA1) { // test how much it takes to add all data1
            checkAddAllData(dataOneArray);
        } else if (testNumber == TEST_ADD_ALL_DATA2) { // test how much it takes to add all data2
            checkAddAllData(dataTwoArray);
        } else if (testNumber == TEST_SEARCH_HI_DATA1) { // data 1 contain "hi"
            checkContain(dataOneArray, SEARCH_VAL_HI, false);
        } else if (testNumber == TEST_SEARCH_NEGATIVE_NUM_DATA1) { // data 1 contain "-13170890158"
            checkContain(dataOneArray, SEARCH_VAL_NEGATIVE_NUM, false);
        } else if (testNumber == TEST_SEARCH_NUM_DATA2) { // data 2 contain "23"
            checkContain(dataTwoArray, SEARCH_VAL_NUM, false);
        } else if (testNumber == TEST_SEARCH_HI_DATA2) { // data 2 contain "hi"
            checkContain(dataTwoArray, SEARCH_VAL_HI, false);
        } else if (testNumber == TEST_ALL) { // run all tests
            checkAddAllData(dataOneArray);
            checkContain(dataOneArray, SEARCH_VAL_HI, true);
            checkContain(dataOneArray, SEARCH_VAL_NEGATIVE_NUM, true);
            checkAddAllData(dataTwoArray);
            checkContain(dataTwoArray, SEARCH_VAL_NUM, true);
            checkContain(dataTwoArray, SEARCH_VAL_HI, true);
        } else {
            System.out.println("Not A Valid Input");
        }
    }

    /**
     * the method responsible to mange the main method
     */
    private static void mangeMain() {
        Scanner input = new Scanner(System.in);
        System.out.println("Which Test would you like to run: \n" +
                "Press 1 to check running time of add data1 \n" +
                "Press 2 to check running time of add data2 \n" +
                "Press 3 to check running time of contain 'hi' in data1 \n" +
                "Press 4 to check running time of contain '-13170890158' in data1 \n" +
                "Press 5 to check running time of contain '23' in data2 \n" +
                "Press 6 to check running time of contain 'hi' in data2 \n" +
                "Press 7 to check all the running time at once");
        int userInput = input.nextInt();
        chooseTest(userInput);
    }


    public static void main(String[] args) {
        mangeMain();
    }
}
