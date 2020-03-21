/**
 * a hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */
public class ClosedHashSet extends SimpleHashSet {

    private static final String DELETED = "deleted object";
    private String[] closedHashTable;
    private static final int QUAD_PROBING_DENOMINATOR = 2;
    private static final int ITEM_IS_FOUND = 1;
    private static final int ITEM_ISNT_FOUND = 0;
    private static final int ITEM_DELETED_SUCCESS = 2;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.closedHashTable = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default
     * initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.closedHashTable = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of
     * initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        this();
        for (String aData : data) {
            add(aData);
        }
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        return loopHelper(searchVal, true) == ITEM_IS_FOUND;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        if (contains(newValue)) {// no duplicate allowed
            return false;
        } else {
            if (checkTableLoad(true) == BIGGER_REHASH) {// need to check rehash
                rehashBiggerTable();
            }
            int valueIndex = findHashIndex(newValue);
            this.closedHashTable[valueIndex] = newValue; // reg add
            this.size++;
            return true;
        }
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (!contains(toDelete)) {
            return false;
        } else {
            loopHelper(toDelete, false);
            if (checkTableLoad(false) == SMALLER_REHASH) {
                rehashSmallerTable();
            }
            this.size--;
            return true;
        }
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     *
     * @param index the index before clamping
     * @return an index properly clamped
     */
    @Override
    protected int clamp(int index) {
        for (int i = 0; i < this.capacity; i++) {
            int quadraticProbing = (i + i * i) / QUAD_PROBING_DENOMINATOR;
            index = (index + quadraticProbing) & capacityMinusOne;
            if ((this.closedHashTable[index] == null) ||
                    (this.closedHashTable[index] == (DELETED))) { // empty or deleted
                return index;
            }
        }
        return index;
    }

    /**
     * in case current load factor is bigger then the upper load factor
     * we will need to rehash all the items into a new table
     */
    @Override
    protected void rehashBiggerTable() {
        int newCapacity = this.capacity * RESIZE_FACTOR;
        String[] temp = this.closedHashTable;
        this.closedHashTable = new String[newCapacity];
        rehash(temp, newCapacity);
    }

    /**
     * in case current load factor is smaller then the lower load factor
     * we will need to rehash all the items into a new table
     */
    @Override
    protected void rehashSmallerTable() {
        int newCapacity = this.capacity / RESIZE_FACTOR;
        String[] temp = this.closedHashTable;
        this.closedHashTable = new String[newCapacity];
        rehash(temp, newCapacity);
    }

    /**
     * the method responsible on resizing the table and rehashing all the items
     *
     * @param oldTable    the table before resizeing , we will rehash its item into a new Table
     * @param newCapacity our future capacity
     */
    private void rehash(String[] oldTable, int newCapacity) {
        setCapacity(newCapacity);
        for (String item : oldTable) {
            if (item != null && item != (DELETED)) {
                int itemIndex = findHashIndex(item);
                this.closedHashTable[itemIndex] = item;
            }
        }
    }

    /**
     * Helper method for delete and contain methods, loop over the items and return an
     * integer that represent
     * if the condition is held
     *
     * @param string    the string value we want to search for
     * @param isContain flag that indicate which method called this one, contain or delete
     * @return integer that indicate the situation
     */
    private int loopHelper(String string, boolean isContain) {
        int valPlace = string.hashCode();
        int quadraticProbing;
        for (int i = 0; i < this.capacity; i++) { // loop
            quadraticProbing = (i + i * i) / QUAD_PROBING_DENOMINATOR;
            valPlace = (valPlace + quadraticProbing) & capacityMinusOne;
            if (isContain) { // contain
                if (this.closedHashTable[valPlace] == null) {
                    return ITEM_ISNT_FOUND;
                } else if (this.closedHashTable[valPlace].equals(string)&&
                        this.closedHashTable[valPlace]!=DELETED) {
                    return ITEM_IS_FOUND;
                }
            } else { // delete
                if ((closedHashTable[valPlace] != null) &&
                        (closedHashTable[valPlace].equals(string)) &&
                        (closedHashTable[valPlace] != (DELETED))) {
                    this.closedHashTable[valPlace] = DELETED;
                    return ITEM_DELETED_SUCCESS;
                }
            }

        }
        return ITEM_ISNT_FOUND;
    }
}
