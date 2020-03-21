import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * an abstract class that implement a shared things all storage unit have
 */
public abstract class Inventory {

    // Master Class related
    protected HashMap<String, Integer> inventory;
    protected int capacity;

    protected int curUsedStorage;


    // Constants
    protected static final int MOVED_TO_LNG_TRM = 1;
    protected static final int SUCCEEDED = 0;
    protected static final int FAILED = -1;

    // Error messages:
    protected static final String GENERAL_ERROR1 =
            "Error: Your request cannot be completed at this time. ";


    protected Inventory() {
        this.inventory = new HashMap<String, Integer>();
        this.curUsedStorage = 0;
    }

    /**
     * adds an item into the locker
     *
     * @param item the item we want to add
     * @param n    the Quntitiy of the item we want to add
     * @return integer defined by the outcome of the addition, explained explicitly in the overriding
     * method in the subclasses
     */
    public abstract int addItem(Item item, int n);


    /**
     * @param type the item type we want to check.
     * @return the number of Items of type the locker contains
     */
    public int getItemCount(String type) {
        if (inventory.containsKey(type)) {
            return this.inventory.get(type);
        }
        return 0;
    }

    /**
     * get the inventory we keep all the items in
     *
     * @return a shallowcopy of the hashmap we use to represent the locker
     */
    public HashMap<String, Integer> getInventory() {
        return new HashMap<String, Integer>(this.inventory);

    }

    /**
     * get the capacity of the locker
     *
     * @return the capacity of the locker
     */
    public int getCapacity() {

        return this.capacity;
    }

    /**
     * get the available capacity in the locker
     *
     * @return tha available capacity of the locker
     */
    public int getAvailableCapacity() {
        if (curUsedStorage >= this.capacity)
            return 0;
        return this.capacity - this.curUsedStorage;
    }

    // helper methods for add item that relevant to both sub class

    /**
     * check if the item is valid
     *
     * @param item the item we want to check
     * @return true if the item is not legal or item is null, else false
     */
    protected boolean invalidItem(Item item) {
        return (item == null || notLegal(item));
    }

    /**
     * check if the item is legal item
     *
     * @param item the item we want to check
     * @return false if the item is not in the legal item list, true otherwise
     */
    protected boolean notLegal(Item item) {
        for (Item legalItem : ItemFactory.createAllLegalItems()) {
            if (legalItem.getType().equals(item.getType()) && legalItem.getVolume() == item.getVolume()) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the number of items that the user want to add is 0, if so we dont have capacity factors, if smaller then 0 negative number return err
     *
     * @param item the item we want to add
     * @param n    the quentity of the item we want to add
     * @return add the item if n=0; return Error if the n is negative
     */
    protected int additionQuantityEdgeCase(Item item, int n) {
        if (n == 0) {
            return addItemToStorage(item, n);
        } else {
            System.out.println(GENERAL_ERROR1);
            return FAILED;
        }
    }

    /**
     * add the item to the item to the locker
     *
     * @param item the item we want to add to the locker
     * @param n    the quantity of the item we watn to add to the locker
     * @return 0 if the item have been successfully added
     */
    protected int addItemToStorage(Item item, int n) {
        this.setInventoryAddition(item, n + this.getItemCount(item.getType()));
        return SUCCEEDED;
    }

    /**
     * adds the item with the given quentity to the hashmap that represent the locker
     *
     * @param item the item we want to add to the locker
     * @param n    the quantity of the item we watn to add to the locker
     */
    protected void setInventoryAddition(Item item, int n) {
        this.curUsedStorage += n * item.getVolume();
        this.inventory.put(item.getType(), n);
    }

    /**
     * check if the locker have enough room for the addition
     *
     * @param item the item we want to check
     * @param n    the item quantity we want to check
     * @return true if the locker do have enough available storage, false otherwise
     */
    protected boolean lockerHasRoomFor(Item item, int n) {
        return (this.getAvailableCapacity() >= item.getVolume() * n);
    }

}
