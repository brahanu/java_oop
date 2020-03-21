import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * class that implement comparator interface, override the compare method to compare by
 * StarRating and alphabet order
 */
public class ArrangeHotelByStarRating implements Comparator<Hotel> {
    private static final int FIRST_ARG = -1;
    private static final int SECOND_ARG = 1;

    /**
     * method that overrides the regular compare method, between 2 object who have more star rating
     * if their star rating is equal sort them by alphabet order
     *
     * @param hotelOne the first object we want to compare
     * @param hotelTwo the second object we want to compare
     * @return -1 if the second argumant have more,1 if the first have more, and if their star rating
     * is equal return an integer representing who is first in alphabet order
     */
    @Override
    public int compare(Hotel hotelOne, Hotel hotelTwo) {

        if (hotelOne.getStarRating() < hotelTwo.getStarRating()) {
            return SECOND_ARG;
        } else if (hotelOne.getStarRating() > hotelTwo.getStarRating())
            return FIRST_ARG;
        else {
            return hotelOne.getPropertyName().compareTo(hotelTwo.getPropertyName());
        }
    }


}
