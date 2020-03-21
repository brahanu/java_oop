import oop.ex3.searchengine.*;
import org.junit.*;


import static org.junit.Assert.*;

/**
 * Test for the BoopingSite
 */
public class BoopingSiteTest {
    private static final String CHECK_BY_CITY_MSG = " check getHotelsInCityByRating;";
    private static final String CHECK_BY_CITY_AND_PROX_MSG = " check getHotelsInCityByProximity;";
    private static final String CHECK_BY_PROX_MSG = " check getHotelsByProximity;";
    private static final String EXPECTED_EMPTY_MSG = " Expected empty Array";
    private static final String EMPTY_TEST_MSG = "Empty File Test Failed;";
    private static final String NULL_TEST_MSG = "Null city input Test Failed;";
    private static final String FALSE_CITY_MSG = "False city input Test Failed;";
    private static final String LRGE_DATA_MSG = "Large Data Set Failed";
    private static final String SORT_BY_CITY = "Sort By City BASIC TEST Failed;";

    /**
     * Test empty file scenarios
     */
    @Test
    public void emptyFileTest() {
        BoopingSite checkNullFile = new BoopingSite("hotels_tst2.txt");
        Hotel[] outputArrayCity = checkNullFile.getHotelsInCityByRating("manali");
        Hotel[] outputArrayProximity = checkNullFile.getHotelsByProximity(20, 10);
        Hotel[] outputArrayCityAndProximity =
                checkNullFile.getHotelsInCityByProximity("manali", 20, 10);
        assertEquals(EMPTY_TEST_MSG +
                CHECK_BY_CITY_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCity.length);
        assertEquals(EXPECTED_EMPTY_MSG +
                CHECK_BY_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayProximity.length);
        assertEquals(EMPTY_TEST_MSG +
                CHECK_BY_CITY_AND_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCityAndProximity.length);
    }

    /**
     * Test null city input scenarios
     */
    @Test
    public void nullCityTest() {
        BoopingSite checkNullCity1 = new BoopingSite("hotels_tst1.txt");
        BoopingSite checkNullCity2 = new BoopingSite("hotels_tst1.txt");
        Hotel[] outputArrayCityAndProximity = checkNullCity2.getHotelsInCityByProximity(null, 20, 10);
        Hotel[] outputArrayCity = checkNullCity1.getHotelsInCityByRating(null);
        assertEquals(NULL_TEST_MSG +
                CHECK_BY_CITY_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCity.length);
        assertEquals(NULL_TEST_MSG +
                CHECK_BY_CITY_AND_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCityAndProximity.length);


    }

    /**
     * Test insertring city that not exist
     */
    @Test
    public void falseCity() {
        BoopingSite checkFalseCity1 = new BoopingSite("hotels_tst1.txt");
        BoopingSite checkFalseCity2 = new BoopingSite("hotels_tst1.txt");
        Hotel[] outputArrayCity = checkFalseCity1.getHotelsInCityByRating("brahan land");
        Hotel[] outputArrayCityAndProximity =
                checkFalseCity2.getHotelsInCityByProximity("brahan land", 30, 60);
        assertEquals(FALSE_CITY_MSG +
                CHECK_BY_CITY_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCity.length);
        assertEquals(FALSE_CITY_MSG +
                CHECK_BY_CITY_AND_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArrayCityAndProximity.length);
    }

    /**
     * Test all hotels in file have the same city scenarios
     */
    @Test
    public void sameCityBasicTest() {
        BoopingSite checkSameCity = new BoopingSite("hotels_tst1.txt");
        Hotel[] outputArray = checkSameCity.getHotelsInCityByRating("manali");
        assertEquals("Sort By City BASIC TEST Failed;" + CHECK_BY_CITY_MSG +
                "Expected Array length  70", 70, outputArray.length);
        for (int i = 0; i < outputArray.length - 1; i++) {
            if (outputArray[i].getStarRating() != outputArray[i + 1].getStarRating()) {
                assertTrue(SORT_BY_CITY + CHECK_BY_CITY_MSG +
                                "The Array is not sorted by Star Rating",
                        outputArray[i].getStarRating() > outputArray[i + 1].getStarRating());
            } else {
                assertTrue(SORT_BY_CITY + CHECK_BY_CITY_MSG +
                                "The Array is not sorted by number of POI",
                        (outputArray[i].getPropertyName().compareTo(outputArray[i + 1].getPropertyName())) < 0);
            }

        }
    }

    /**
     * Test basic functionlty of hotels by proximity
     */
    @Test
    public void basicByProximityTest() {
        BoopingSite basicProximity = new BoopingSite("hotels_tst1.txt");
        Hotel[] outputArray1 = basicProximity.getHotelsByProximity(0, 0);
        Hotel[] outputArray2 = basicProximity.getHotelsByProximity(30, 60);
        Hotel[] outputArray3 = basicProximity.getHotelsByProximity(-180, 180);
        checkIfArraySortedByProximity(outputArray1, 0, 0);
        checkIfArraySortedByProximity(outputArray2, 30, 60);
        checkIfArraySortedByProximity(outputArray3, -180, 180);

    }

    /**
     * Test inserting out of bound points to the proximity related methods
     */
    @Test
    public void outOfBoundProximityPointsTest() {
        BoopingSite outOfBoundProximity1 = new BoopingSite("hotels_tst1.txt");
        BoopingSite outOfBoundProximity2 = new BoopingSite("hotels_tst1.txt");
        BoopingSite outOfBoundProximity3 = new BoopingSite("hotels_tst1.txt");
        BoopingSite outOfBoundProximity4 = new BoopingSite("hotels_tst1.txt");
        Hotel[] outputArray1 = outOfBoundProximity1.getHotelsByProximity(91, 181);
        Hotel[] outputArray2 = outOfBoundProximity3.getHotelsByProximity(-91, -181);
        Hotel[] outputArray3 = outOfBoundProximity2.getHotelsInCityByProximity("manali", -91, -181);
        Hotel[] outputArray4 = outOfBoundProximity4.getHotelsInCityByProximity("manali", -91, 33);
        assertEquals("Coordinate Out of Bound Test Failed;" + CHECK_BY_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArray1.length);
        assertEquals("Coordinate Out of Bound Test Failed;" + CHECK_BY_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArray3.length);
        assertEquals("Coordinate Out of Bound Test Failed;" + CHECK_BY_CITY_AND_PROX_MSG + EXPECTED_EMPTY_MSG, 0, outputArray2.length);
    }

    /**
     * Test the methods with large data set
     */
    @Test
    public void largeDataBaseTest() {
        BoopingSite checkLargeDataSetProximityAndCity = new BoopingSite("hotels_dataset.txt");
        BoopingSite checkLargeDataSetProximity = new BoopingSite("hotels_dataset.txt");
        BoopingSite checkLargeDataSetCity = new BoopingSite("hotels_dataset.txt");
        Hotel[] outputArrayCity = checkLargeDataSetProximityAndCity.getHotelsInCityByProximity("mumbai", 0, 0);
        Hotel[] outputArrayProximity = checkLargeDataSetProximityAndCity.getHotelsInCityByProximity("delhi", 30, 90);
        Hotel[] outputArrayCityProximity = checkLargeDataSetProximityAndCity.getHotelsInCityByProximity("goa", 90, 90);
        assertEquals(LRGE_DATA_MSG, 65, outputArrayCity.length);
        assertEquals(LRGE_DATA_MSG, 137, outputArrayProximity.length);
        checkIfArraySortedByProximity(outputArrayProximity, 30, 90);
        assertEquals(LRGE_DATA_MSG, 220, outputArrayCityProximity.length);
        checkIfArraySortedByProximity(outputArrayCityProximity, 90, 90);

    }

    /**
     * helper method, calculate the distance
     *
     * @return distance of 2 point from eachother
     */
    private double calcluateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    /**
     * helper method, check if array is sorted by proximity and NumPOI
     */
    private void checkIfArraySortedByProximity(Hotel[] array, double coor1, double coor2) {
        for (int i = 0; i < array.length - 1; i++) {
            double dis1 = calcluateDistance(coor1, coor2, array[i].getLatitude(), array[i].getLongitude());
            double dis2 = calcluateDistance(coor1, coor2, array[i + 1].getLatitude(), array[i + 1].getLongitude());
            if (dis1 != dis2) {
                assertTrue("Array is not sorted by Proximity", dis1 < dis2);


            } else {
                assertTrue("Array is not sorted by Number of POI when the distance is equal"
                        , (array[i].getNumPOI() >= array[i + 1].getNumPOI()));
            }
        }
    }

}


