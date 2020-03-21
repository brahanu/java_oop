import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * class that implement comparator interface,override the compare method to compare by Proximity and numPOI
 */
public class ArrangeHotelByProximity implements Comparator<Hotel> {
    private double latitude;
    private double longitude;
    private static final int FIRST_IS_CLOSER = -1;
    private static final int SECOND_IS_CLOSER = 1;

    /**
     * constructor for the comparator, get 2 inputs to define to what point we want to compare
     * two object distance from
     * @param latitude the first coordinate
     * @param longitude  the second coordinate
     */
    public ArrangeHotelByProximity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     *  compare method who compare 2 hotel object by distance from a given point
     * @param hotelOne the first hotel
     * @param hotelTwo the seocond hotel
     * @return -1 if the first hotel is closer to the given point, 1 if the second one is closer
     *          if the 2 hotels have the same distance from the point return their order by the number of POI
     */
    public int compare(Hotel hotelOne, Hotel hotelTwo) {
        double hotel1Latitude = hotelOne.getLatitude();
        double hotel1Longitude = hotelOne.getLongitude();
        double hotel2Latitude = hotelTwo.getLatitude();
        double hotel2Longitude = hotelTwo.getLongitude();
        double disHotel1 = calculateDistance(hotel1Latitude, hotel1Longitude);
        double disHotel2 = calculateDistance(hotel2Latitude, hotel2Longitude);
        if (disHotel1 < disHotel2) {
            return FIRST_IS_CLOSER;
        } else if (disHotel1 > disHotel2)
            return SECOND_IS_CLOSER;
        else {
            return Integer.compare(hotelTwo.getNumPOI(),hotelOne.getNumPOI());
        }
    }

    /**
     * helper method, calculare the distance between 2 points and the given point
     * @param x1 ththe first coordinate
     * @param y1 the second coordinate
     * @return the distance of the x1,y1 from the given by the constructor points
     */
    private double calculateDistance(double x1, double y1) {
        return Math.sqrt(Math.pow((latitude - x1),2) + Math.pow((longitude - y1),2));
    }
}
