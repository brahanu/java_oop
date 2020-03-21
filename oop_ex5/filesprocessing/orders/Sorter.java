package filesprocessing.orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The class will use the orders defined in this package and sort the file according to them
 */
public class Sorter {

    private static final int EQUAL_VAL = 0;
    private ArrayList<File> arrayList;
    private Order order;

    /**
     * constructor for the Sorter class, Wraps the sorting algorithm-in our case quickSort
     *
     * @param arrayList the array we want to sort
     * @param order     the orders we want our files will be sorted upon
     */
    public Sorter(ArrayList<File> arrayList, Order order) {
        this.arrayList = arrayList;
        this.order = order;
    }

    /**
     * the method sort the array using the sorting algorithm
     */
    public void sortIt() {
        quickSort(this.arrayList, 0, arrayList.size() - 1, this.order);
    }

    /**
     * helper method for the quickSort method, will divide the method with a random pivot point
     *
     * @param arrayList the arrayList we want to sort
     * @param start     the array first index
     * @param end       the array last index
     * @param order     the orders which the array will be sorted upon
     * @return an integer that represent the place that the other objects are sorted upon
     */
    private static int partition(ArrayList<File> arrayList, int start, int end, Order order) {
        int random = start + ((int) Math.random() * (arrayList.size())) / (end - start + 1);
        int last = end;
        Collections.swap(arrayList, random, end);
        end--;
        while (start <= end) {
            if (order.compare(arrayList.get(start), arrayList.get(last)) < EQUAL_VAL)
                start++;
            else {
                Collections.swap(arrayList, start, end);
                end--;
            }
        }
        Collections.swap(arrayList, start, last);
        return start;
    }

    /**
     * quickSort algorithm as we implemented as we learned in DAST
     *
     * @param arrayList the arrayList we want to sort
     * @param start     the array first index
     * @param end       the array last index
     * @param order     the orders which the array will be sorted upon
     */
    public void quickSort(ArrayList<File> arrayList, int start, int end, Order order) {
        if (start >= end) return;
        if (start < 0) return;
        if (end > arrayList.size() - 1) return;

        int pivot = partition(arrayList, start, end, order);
        quickSort(arrayList, start, pivot - 1, order);
        quickSort(arrayList, pivot + 1, end, order);
    }
}




