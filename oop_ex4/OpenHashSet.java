import java.util.*;

/**
 * a hash-set based on chaining. Extends SimpleHashSet.
 */
public class OpenHashSet extends SimpleHashSet {

    /**
     * wrapper-class that has a LinkedList<String> and delegates methods to it, and have
     * an array of that class instead
     */
    private WrappedLinkedList[] openHashTable;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.openHashTable = new WrappedLinkedList[INITIAL_CAPACITY];
        bucketCreator(openHashTable, INITIAL_CAPACITY);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial
     * capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.openHashTable = new WrappedLinkedList[INITIAL_CAPACITY];
        bucketCreator(openHashTable, INITIAL_CAPACITY);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial
     * capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
        this();
        for (String aData : data) {
            add(aData);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (contains(newValue) || newValue == null) {
            return false;
        } else {
            if (checkTableLoad(true) == BIGGER_REHASH) {
                rehashBiggerTable();
            }
            int valIndex = findHashIndex(newValue);
            this.openHashTable[valIndex].getLinkedList().add(newValue);
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
        if (!contains(toDelete) || toDelete == null) {
            return false;
        } else {
            int valToDeleteIndex = findHashIndex(toDelete);
            this.openHashTable[valToDeleteIndex].getLinkedList().remove(toDelete);
            if (checkTableLoad(false) == SMALLER_REHASH) {
                rehashSmallerTable();
            }
            this.size--;
            return true;
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
        if (searchVal == null) {
            return false;
        } else {
            int valueIndex = findHashIndex(searchVal);
            return this.openHashTable[valueIndex].getLinkedList().contains(searchVal);
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
        return index & capacityMinusOne;
    }

    /**
     * in case current load factor is bigger then the upper load factor
     * we will need to rehash all the items into a new table
     */
    @Override
    protected void rehashBiggerTable() {
        int newCapacity = this.capacity * RESIZE_FACTOR;
        WrappedLinkedList[] newTable = new WrappedLinkedList[newCapacity];
        bucketCreator(newTable, newCapacity);
        this.openHashTable = rehash(newTable, newCapacity);
    }

    /**
     * in case current load factor is smaller then the lower load factor
     * we will need to rehash all the items into a new table
     */
    @Override
    protected void rehashSmallerTable() {
        int newCapacity = this.capacity / RESIZE_FACTOR;
        WrappedLinkedList[] newTable = new WrappedLinkedList[newCapacity];
        bucketCreator(newTable, newCapacity);
        this.openHashTable = rehash(newTable, newCapacity);
    }

    /**
     * rehash all the item from the old table into a new table
     *
     * @param newTable    our new Table, we will be adding all the items from the old one
     *                    to this one
     * @param newCapacity our new capacity, defined by the resize factor
     * @return a new hashTable with the items as the old one but different size
     */
    private WrappedLinkedList[] rehash(WrappedLinkedList[] newTable, int newCapacity) {
        setCapacity(newCapacity);
        for (WrappedLinkedList bucket : this.openHashTable) {
            if (!bucket.getLinkedList().isEmpty()) {
                for (int j = 0; j < bucket.getLinkedList().size(); j++) {
                    String val = bucket.getLinkedList().get(j);
                    int valIndex = findHashIndex(val);
                    newTable[valIndex].getLinkedList().add(val);
                }
            }
        }
        return newTable;
    }

    /**
     * the method create bucket for each cell in the array
     *
     * @param table  the table we want to turn into a proper hashTable
     * @param hashTableLength the number of buckets we need to create
     */
    private void bucketCreator(WrappedLinkedList[] table, int hashTableLength) {
        for (int i = 0; i < hashTableLength; i++) {
            table[i] = new WrappedLinkedList();
        }
    }

    /**
     * nested class that represent a wrapped linked list, the "buckets" of the table
     */
    private class WrappedLinkedList {

        private LinkedList<String> linkedLst;

        /**
         * a constructor for the class, creates LinkedList
         */
        private WrappedLinkedList() {
            this.linkedLst = new LinkedList<String>();
        }

        /**
         * a getter method for the linked list object we created
         *
         * @return linked list
         */
        protected LinkedList<String> getLinkedList() {
            return this.linkedLst;
        }

    }
}