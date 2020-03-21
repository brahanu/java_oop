import oop.ex3.spaceship.Item;

/**
 * Class that implement the longTermStorage object
 */
public class LongTermStorage extends Inventory {
    /**
     * constracor for this class
     */
    public LongTermStorage() {
        this.capacity = 1000;
    }

    /**
     * This method adds n Items of the given type to the longterm
     * storage unit. If the action is successful, this method should return 0, else return -1
     *
     * @param item the item we want to add
     * @param n    the Quntitiy of the item we want to add
     * @return 0 if the addition was successful , -1 otherwise
     */
    @Override
    public int addItem(Item item, int n) {
        if (invalidItem(item)) {
            System.out.println(GENERAL_ERROR1);
            return FAILED;
        } else if (n <= 0) {
            return additionQuantityEdgeCase(item, n);
        } else if (lockerHasRoomFor(item, n)) {
            return addItemToStorage(item, n);
        } else {
            System.out.println(GENERAL_ERROR1 +
                    "Problem: no room for " + n + " items of type " + item.getType() + " in the longTermStorage");
            return FAILED;
        }
    }

    /**
     * . This method resets the long-term storage’s inventory
     */
    public void resetInventory() {
        this.inventory.clear();
        this.curUsedStorage = 0;
    }


}
