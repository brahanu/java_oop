package filesprocessing;

import filesprocessing.filters.*;
import filesprocessing.orders.*;


/**
 * the class will be responsible connecting between Order and Filter objects
 */
public class Section {

    private String filter;
    private String order;
    private int filterLine;
    private int orderLine;

    /**
     * constructor fot the section class
     *
     * @param filter     the filters in the section
     * @param filterLine the filters line in the commandFile, needed for the warning
     * @param order      the orders in the section
     * @param orderLine  the orders line in the commandFile, needed for the warning
     */
    Section(String filter, int filterLine, String order, int orderLine) {
        this.filter = filter;
        this.order = order;
        this.filterLine = filterLine;
        this.orderLine = orderLine;
    }

    /**
     * a getter method for the filters
     *
     * @return the filters using the filters factory, if an exception was raised in the "below"
     * classes will return the DEFAULT filters
     */
    public Filter getFilter() {
        try {
            return FilterFactory.filterFactory(this.filter);
        } catch (TypeOneException e) {
            e.msg(this.filterLine);
            return new All();

        }

    }

    /**
     * a getter method for the orders
     *
     * @return the needed orders using the orderFactory, an exception was raised in the "below"
     * * classes will return the DEFAULT orders
     */
    public Order getOrder() {
        try {
            return OrderFactory.orderFactory(this.order);
        } catch (TypeOneException e) {
            e.msg(this.orderLine);
        }
        return new OrderByName();

    }


}
