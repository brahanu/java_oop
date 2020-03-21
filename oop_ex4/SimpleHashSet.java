/**
 * an abstract superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {
    private static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    private static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    private static final int MIN_SIZE = 1; // minimum capacity size
    private static final int EMPTY_TABLE_SIZE = 0;
    protected static final int INITIAL_CAPACITY = 16;
    private float upperLoadFactor;
    private float lowerLoadFactor;
    protected int capacity;
    protected int capacityMinusOne;
    protected int size;
    protected static final int BIGGER_REHASH = 1;
    protected static final int SMALLER_REHASH = -1;
    protected static final int NO_REHASH = 0;
    protected static final int RESIZE_FACTOR = 2;


    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public SimpleHashSet() {
        baseValues();
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.capacityMinusOne = this.capacity - 1;
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default
     * initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        baseValues();
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.capacityMinusOne = this.capacity - 1;
    }

    /**
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public abstract boolean add(String newValue);

    /**
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public abstract boolean contains(String searchVal);

    /**
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public abstract boolean delete(String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * capacity in class SimpleHashSet
     *
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     * getter for the upperLoadFactor
     *
     * @return The higher load factor of the table.
     */
    public float getUpperLoadFactor() {
        return this.upperLoadFactor;
    }

    /**
     * getter for the lowerLoadFactor
     *
     * @return The lower load factor of the table.
     */
    public float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }

    /**
     * a setter to the capacity field
     *
     * @param newCapacity the capacity which we want to set
     */
    protected void setCapacity(int newCapacity) {
        if (newCapacity >= MIN_SIZE) {
            this.capacity = newCapacity;
            this.capacityMinusOne = newCapacity - 1;
        } else {
            this.capacity = MIN_SIZE;
            this.capacityMinusOne = 0;
        }
    }

    /**
     * Clamps hashing indices to fit within the current table capacity
     *
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected abstract int clamp(int index);

    /**
     * get the index that our val should have in the hashTable and then clamp it
     * to our own hashtable size range
     *
     * @param val the value which we want to find its index in our hashtable
     * @return the index of the value after clamping
     */
    protected int findHashIndex(String val) {
        return clamp(val.hashCode());
    }

    /**
     * check if we need to rehash our table using the upper/lower load factors
     *
     * @return 1 if we need a bigger table, 0 if dont need to rehash, -1 if need smaller table
     */
    protected int checkTableLoad(boolean isAdd) {
        int checkUpper = this.size + 1;
        int checkLower = this.size - 1;
        float curLoadUpper = (float) (checkUpper) / (float) this.capacity;
        float curLoadLower = (float) (checkLower) / (float) this.capacity;
        if (isAdd && (curLoadUpper > this.upperLoadFactor || curLoadUpper > 1)) {
            return BIGGER_REHASH;
        }
        if (!isAdd && (curLoadLower < this.lowerLoadFactor && curLoadUpper <= 1)) {
            return SMALLER_REHASH;
        }
        return NO_REHASH;
    }

    /**
     * in case current load factor is bigger then the upper load factor
     * we will need to rehash all the items into a new table
     */
    protected abstract void rehashBiggerTable();

    /**
     * in case current load factor is smaller then the lower load factor
     * we will need to rehash all the items into a new table
     */
    protected abstract void rehashSmallerTable();

    /**
     * define the hashtable fields into the default values as defined in the pdf
     */
    private void baseValues() {
        this.capacity = INITIAL_CAPACITY;
        this.size = EMPTY_TABLE_SIZE;
    }
}