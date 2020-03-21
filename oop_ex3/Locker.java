import oop.ex3.spaceship.Item;

/**
 * class That define a single locker
 */
public class Locker extends Inventory {
    private static final int CONTRAST_FAILED = -2;
    private static final String CONTRAST_ITEM1 = "baseball bat";
    private static final String CONTRAST_ITEM2 = "football";
    private static final String WARNING_LONG_TERM_USED = "Warning: Action successful, but has caused items to be move to storage";
    private static final double FIFTH = 0.2;
    private static final double HALF = 0.5;

    private static LongTermStorage longTermStorage;

    /**
     * construtor for the locekr object
     * @param capacity the capacity of the locker
     */
    public Locker(int capacity) {
        if (capacity < 0) {
            System.out.println(GENERAL_ERROR1);
            capacity = 0;
        }
        this.capacity = capacity;
        longTermStorage = new LongTermStorage();
    }


    /**
     * @param item the item we want to add to the locker
     * @param n    the number of items of a given type we want to add to the locker
     * @return int value defined according to the outcome of the addition
     * succeeded 0, failed -1, the locker have contrast item -2, moved to longterm storage 1,
     */
    @Override
    public int addItem(Item item, int n) {
        if (contrastItems(item)) { // section C, contrast items first condition on adding items
            return contrastItemsInStorage(item);
        } else if (invalidItem(item)) {  // check if the item is valid
            System.out.println(GENERAL_ERROR1);
            return FAILED;
        } else if (n <= 0) { // adds 0 items if needed else return ERROR
            return additionQuantityEdgeCase(item, n);
        } else if (lockerHasRoomFor(item, n)) { // check if the locker have room for all the items
            if (needsLongTermStorage(item, n)) { //  check if the the item volume will be needed to move to long term
                return addWithLongTermStorage(item, n);
            } else {
                return addItemToStorage(item, n);
            }
        } else { // locker dont have enough room for all the items
            System.out.println(GENERAL_ERROR1 + "Problem: no room for " + n + " items of type " + item.getType());
            return FAILED;
        }
    }

    /**
     * This method removes n Items of the type type from
     * the locker. If the action is successful, this method should return 0, else return -1
     *
     * @param item the Item type type we want to delete
     * @param n    the number of items from type type we want to delete
     * @return if the method succssefuly deleted the items return 0, else return -1
     */
    public int removeItem(Item item, int n) {
        if (invalidItem(item)) {
            System.out.println(GENERAL_ERROR1);
            return FAILED;
        } else if (n < 0) {
            return negativeSubtraction(item);
        } else if (removeIsntPossible(item, n)) {
            return notEnoughItemsForRemove(item, n);
        } else {
            setInventorySubtraction(item, n);
            return SUCCEEDED;
        }

    }

    //additem helper methods

    /**
     * check if the item we want to add isnt contradicting an item that alerdy in the storage
     *
     * @param item the item we want to check
     * @return true if there is a contradicting item in the the locker
     */
    private boolean contrastItems(Item item) {
        return (this.getItemCount(CONTRAST_ITEM1) != 0 && item.getType().equals(CONTRAST_ITEM2)) ||
                (this.getItemCount(CONTRAST_ITEM2) != 0 && item.getType().equals(CONTRAST_ITEM1));
    }

    /**
     * if there is a contradicting item in the storage prints out a message, and return -1;
     *
     * @param item the item we want to add to the locker
     * @return -1
     */
    private int contrastItemsInStorage(Item item) {
        System.out.println(GENERAL_ERROR1 +
                " Problem: the locker cannot contain items of type " + item.getType() +
                ", as it contains a contradicting item");
        return CONTRAST_FAILED;
    }

    /**
     * check if with the addition the storage held by the item will be more the 50%
     *
     * @param item the item we want to add
     * @param n    the item quentity we want to add
     * @return true if the item we hold more then 50%, false otherwise
     */
    private boolean needsLongTermStorage(Item item, int n) {
        return (item.getVolume() * (this.getItemCount(item.getType()) + n) > this.getCapacity() * HALF);
    }

    /**
     * direct the item addition into the longTermStorage
     *
     * @param item the item that will pass 50% of the regular storage capcity
     * @param n    the quentitiy that made the item capcity to be above 50% of all capacity
     * @return -1 if there is no place in the long term storage,1 if we added the item to longtermstorage
     */
    private int addWithLongTermStorage(Item item, int n) {
        int totalItemQuantity = this.getItemCount(item.getType()) + n;
        int lockerQuantity = (int) (FIFTH * this.getCapacity()) / item.getVolume();
        int longTermQuantity = totalItemQuantity - lockerQuantity;
        if (getLongTerm().getAvailableCapacity() >= longTermQuantity * item.getVolume()) {
            getLongTerm().addItem(item, longTermQuantity);
            this.setInventoryAddition(item, lockerQuantity);
            System.out.println(WARNING_LONG_TERM_USED);
            return MOVED_TO_LNG_TRM;
        } else {
            System.out.println(GENERAL_ERROR1);
            return FAILED;
        }

    }
    //remove helper methods

    /**
     * the method doing the actual modyifing to the hashtable we created
     *
     * @param item the item we want to subtract
     * @param n    the number of items we want to subtract
     */
    private void setInventorySubtraction(Item item, int n) {
        int prevQuantity = this.getItemCount(item.getType());
        int newQuantity = prevQuantity - n;
        this.inventory.put(item.getType(), newQuantity);
        curUsedStorage -= n * item.getVolume();
    }

    /**
     * check if the remove is possible
     *
     * @param item the item we want to subtract
     * @param n    the number of items we want to subtract
     * @return true if we cant subtract false otherwise
     */
    private boolean removeIsntPossible(Item item, int n) {
        return this.getItemCount(item.getType()) < n || this.getItemCount(item.getType()) == 0;
    }

    /**
     * the method define  the output of removeItem when there are not enough items for the remove
     *
     * @param item the item we want to subtract
     * @param n    the number of items we want to subtract
     * @return -1 as defined in the pdf
     */
    private int notEnoughItemsForRemove(Item item, int n) {
        System.out.println(GENERAL_ERROR1 +
                "Problem: the locker does not contain " + n + " items of type " + item.getType());
        return FAILED;
    }

    /**
     * the method define  the output of removeItem when n is negative
     *
     * @param item the item we want to subtract
     * @return -1 as defined in the pdf
     */
    private int negativeSubtraction(Item item) {
        System.out.println(GENERAL_ERROR1 + "Problem: cannot remove negative number of items of type " + item.getType());
        return FAILED;
    }
    // getter for the longTerm

    /**
     * getter for the longTermStorage object
     *
     * @return the longTermStorage object we created
     */
    protected LongTermStorage getLongTerm() {
        return longTermStorage;
    }

}
