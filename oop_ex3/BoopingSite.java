import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * class that define a search engine for hotels
 */
public class BoopingSite {

    private Hotel[] generalHotelList;

    /**
     * This constructor receives as parameter a string which is the name of the dataset and
     * create array of hotel object
     *
     * @param name the name of the datset of hotels, passed to getHotels(String fileName)
     *             to create an array of hotels
     */
    public BoopingSite(String name) {
        generalHotelList = HotelDataset.getHotels(name);
    }

    /**
     * the method return an array of hotels in a given city sorted by Star rating, if 2 hotels are
     * in the same city and hae the same star rating they will be sorted by alphabet order
     *
     * @param city the location of all the hotels we want to sort
     * @return an array of hotels located in given city soted by Star rating from the
     * highest to lowest hotels with the same star rating will be sorted by alphabet order
     */

    public Hotel[] getHotelsInCityByRating(String city) {
        ArrayList<Hotel> bestHotelInCity = getAllHotelByCity(city);
        ArrangeHotelByStarRating compareByStar = new ArrangeHotelByStarRating();
        Collections.sort(bestHotelInCity, compareByStar);

        return bestHotelInCity.toArray(new Hotel[bestHotelInCity.size()]);
    }

    /**
     * the method return array of hotels sorted by distance from a given point, if 2 hotels are in the
     * same distance sort them by the number f POI
     *
     * @param latitude  first coordinate
     * @param longitude second coordinate
     * @return array of hotels sorted by distance from the given point and by POI
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        ArrayList<Hotel> bestHotelByDistance = new ArrayList<Hotel>();
        if (coordinateCheck(latitude, longitude)) {
            return bestHotelByDistance.toArray(new Hotel[bestHotelByDistance.size()]);
        } else {
            Collections.addAll(bestHotelByDistance, generalHotelList);
            ArrangeHotelByProximity compareByDistance = new ArrangeHotelByProximity(latitude, longitude);
            Collections.sort(bestHotelByDistance, compareByDistance);

            return bestHotelByDistance.toArray(new Hotel[bestHotelByDistance.size()]);
        }
    }

    /**
     * the method sort hotels from a given city by distance from a given point and by number of POI
     *
     * @param city      the location of all the hotels we want to sort
     * @param latitude  point first coordinate
     * @param longitude point second coordinate
     * @return array of hotels from given city sorted by distance from a given point and by POI
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        ArrayList<Hotel> allHotelInCity = new ArrayList<Hotel>();
        if (coordinateCheck(latitude, longitude)) {
            return allHotelInCity.toArray(new Hotel[allHotelInCity.size()]);
        } else {
            allHotelInCity = getAllHotelByCity(city);
            ArrangeHotelByProximity compareByDistance = new ArrangeHotelByProximity(latitude, longitude);
            Collections.sort(allHotelInCity, compareByDistance);

            return allHotelInCity.toArray(new Hotel[allHotelInCity.size()]);


        }
    }

    private boolean coordinateCheck(double latitude, double longitude) {
        return latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180;
    }

    /**
     * filter hotels by city from the given txt file
     *
     * @param city the city we want to giter upon
     * @return list with all the hotels that in the given city
     */
    private ArrayList<Hotel> getAllHotelByCity(String city) {
        ArrayList<Hotel> hotelArrayList = new ArrayList<Hotel>();
        for (Hotel aGeneralHotelList : generalHotelList) {
            if (aGeneralHotelList.getCity().equals(city)) {
                hotelArrayList.add(aGeneralHotelList);
            }
        }
        return hotelArrayList;
    }

}
