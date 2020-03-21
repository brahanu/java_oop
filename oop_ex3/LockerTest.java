import oop.ex3.spaceship.*;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Tests for locker CLass
 */
public class LockerTest {

    private Locker locker0;
    private Locker locker1;
    private Locker locker2;
    private Locker locker3;

    private Item[] itemList = ItemFactory.createAllLegalItems();

    /**
     * intiate all the needed objected for the test, and restart them before every test
     */
    @Before
    public void createStorage() {

        locker0 = new Locker(0);
        locker1 = new Locker(10);
        locker2 = new Locker(100);
        locker3 = new Locker(1000);
        // in case the user created a longTermStorage for each locker we still need to reset all of the possibilities  @TODO check the condition
        locker0.getLongTerm().resetInventory();
        locker1.getLongTerm().resetInventory();
        locker2.getLongTerm().resetInventory();
        locker3.getLongTerm().resetInventory();

    }


    // Basic functionality's:

    /**
     * Test if the Init working, meaning check if the the constructor creating a locker
     */
    @Test
    public void checkLockerInit() {
        locker0 = new Locker(0); // check if i can init with 0 capacity
        assertNotNull(locker0);
        locker1 = new Locker(10); // check if i can init with 10 capacity
        assertNotNull(locker1);
    }

    /**
     * Test if the method works and  if it possible to init with negative capacity
     */
    @Test
    public void lockerGetCapacityTest() {

        assertEquals(0, locker0.getCapacity());
        assertEquals(10, locker1.getCapacity());
        assertEquals(100, locker2.getCapacity());
        assertEquals(1000, locker3.getCapacity());
        Locker locker = new Locker(-1);
        assertEquals(0, locker.getCapacity()); // shouldnt create a
    }

    /**
     * Test getAvailableCapacity,
     * 1: sanity check adds item, check if the getAvilableCapcity updated
     * 2: adds items with total volume of 60 >50% expected that only 20% (20 volume worth) should stay after
     * 3: adds the item worth 60 volume should take all of it into the longTerm
     */
    @Test
    public void lockerGetAvailableCapacityTest() {
        assertEquals(1000, locker3.getAvailableCapacity());
        assertEquals(0, locker3.addItem(itemList[3], 10));
        assertEquals(900, locker3.getAvailableCapacity());
        assertEquals(1, locker2.addItem(itemList[3], 6)); // 2 stay in locker 4 to longterm
        assertEquals(80, locker2.getAvailableCapacity()); // 100-2*10
        assertEquals(1, locker2.addItem(itemList[3], 6)); // 100*0.2->20vol
        assertEquals(60, locker2.getAvailableCapacity()); // 2 to locker 4 to longterm
    }

    /**
     * Test getItemCount, 3 test
     * 1: adds zero amount of helmet, size 1, check the amount after the addition, expected 0
     * 2: check the itemcount for not existing item,expected 0
     * 3: regular check, adds 15 of helmet, size 1, and check the itemcount expected 15
     */
    @Test
    public void lockerGetItemCountTest() {
        // adds some items into the lockers
        assertEquals(0, locker1.addItem(itemList[0], 0)); // adds zero items into the locker
        assertEquals(0, locker1.getItemCount(itemList[0].getType()));
        assertEquals(0, locker2.addItem(itemList[0], 3));
        assertEquals(0, locker2.getItemCount("00!@#")); //try to get item count for false type
        assertEquals(0, locker2.addItem(itemList[1], 15));
        assertEquals(15, locker2.getItemCount(itemList[1].getType()));
    }

    /**
     * Test if the Hashmap that represent the locker can be edited from outside
     * hashmap is immutable object if the coder returnerd the orginial one i should be able to change it not thru
     * the method add/remove/reset
     */
    @Test
    public void lockerGetInventoryTest() {
        Locker locker = new Locker(100);
        HashMap<String, Integer> checkMap = locker.getInventory();
        locker.addItem(itemList[0], 3);
        assertTrue("getInventory Test Failed: check if the inventory updated after addition",
                locker.getInventory().containsKey(itemList[0].getType()));
        assertEquals("getInventory Test Failed: check if the inventory updated after addition",
                3, locker.getItemCount(itemList[0].getType()));
        checkMap.put("blabla", 5);
        assertEquals("getInventory Test Failed: I Have access to the original inventory Hashmap",
                0, locker.getItemCount("blabla"));
    }


    // by scenarios

    /**
     * Test adding 0 items into the locker; Test adding negative number of items;
     */
    @Test
    public void lockerAddNonPositiveQuantity() {
        assertEquals(0, locker3.addItem(itemList[0], 0));
        assertEquals(-1, locker3.addItem(itemList[1], -1));
        assertTrue(locker3.getInventory().containsKey(itemList[0].getType()));
        assertEquals(0, locker3.getItemCount(itemList[0].getType()));
        assertEquals(1000, locker3.getAvailableCapacity());
        assertEquals(0, locker3.getItemCount(itemList[1].getType()));

    }

    /**
     * Test initiaite a locker with a negative capacity, expect empty hashTable
     */
    @Test
    public void negativeCapacityTest() {
        Locker locker4 = new Locker(-1);
        HashMap<String, Integer> emptyHash = new HashMap<String, Integer>();
        locker4.addItem(itemList[2], 1); // shouldnt be able to add item
        assertEquals(locker4.getInventory(), emptyHash); // check if the hashmap is empty
    }

    /**
     * Test adding one item that make the storage full, and then check if the item have been passed to the
     * longTerm or stayed in the locker: locker capacity 10, item volume 10, should go to the longTerm
     */
    @Test
    public void addOneItemMakeTheStorageFullTest() {
        locker1.addItem(itemList[3], 1);
        assertEquals(0, locker1.getItemCount(itemList[3].getType()));
        assertEquals(1, locker1.getLongTerm().getItemCount(itemList[3].getType()));
        assertEquals(10, locker1.getAvailableCapacity());
        assertEquals(990, locker1.getLongTerm().getAvailableCapacity());
        assertEquals(10, locker1.getCapacity()); // locker capacity should be untouched
    }

    /**
     * Test adding contrast item into the same locker, should fail and return -1
     */
    @Test
    public void addContrastItemTest() {
        addTenItemsOfNoContrast();
        assertEquals(-2, locker3.addItem(itemList[4], 10)); // should fail== meaning return -2
        assertEquals(0, locker3.getItemCount(itemList[4].getType()));
    }

    /**
     * Test removing negative amount from the locker
     */
    @Test
    public void removeNegativeTest() {
        addTenItemsOfNoContrast();
        assertEquals(-1, locker3.removeItem(itemList[0], -1));
        assertEquals(10, locker3.getItemCount(itemList[3].getType()));
    }

    /**
     * Test adding contrast item after removing the first contrast item, should be succeeded
     */
    @Test
    public void addContrastItemAfterRemoveTest() {
        addTenItemsOfNoContrast();
        locker3.removeItem(itemList[0], 10);
        locker3.addItem(itemList[4], 10);
        assertEquals(0, locker3.getItemCount(itemList[0].getType()));
        assertEquals(10, locker3.getItemCount(itemList[4].getType()));
    }

    /**
     * Test add items with not legal input, null and negative quantity
     */
    @Test
    public void addItemIllegalInputTest() {
        assertEquals(-1, locker3.addItem(null, 10));
        assertEquals(-1, locker3.addItem(itemList[0], -10));
    }

    /**
     * Test removing items with illegal inputs, null of negative numbers
     */
    @Test
    public void removeItemIllegalInputTest() {
        assertEquals(-1, locker3.removeItem(null, 10));
        assertEquals(-1, locker3.removeItem(itemList[0], -10));
    }

    // long Term Connection test:

    /**
     * Test how many instance of the longtermstorage the user created
     * (possible outcome created one with every init of a locker, which is not good)
     */
    @Test
    public void checkHowManyLongTermStorage() {
        assertEquals(1, locker2.addItem(itemList[3], 7));
        assertEquals(1, locker3.addItem(itemList[3], 55));

        assertEquals(locker3.getLongTerm(), locker2.getLongTerm());
    }

    /**
     * Test what happen to longterm when I add more then the locker capacity in one addition
     */
    @Test
    public void addOnceToFullStorageTest() {
        assertEquals(1, locker2.addItem(itemList[2], 12));
        assertEquals(8, locker2.getLongTerm().getItemCount(itemList[2].getType()));
        assertEquals(960, locker2.getLongTerm().getAvailableCapacity());
    }

    /**
     * adds 18 items of type helmet size 3, volume 5 to a locker with a capacity of 100
     * Test if the 4 items stays in the regular locker meaning (20% out of 100 == 100)
     * Test if 14 items passed to the longTermStorage (18-4 = 14=> 14*5=70)
     */
    @Test
    public void checkTwentyPercentStayInStorageTest() {
        assertEquals(1, locker2.addItem(itemList[2], 18));
        assertEquals(930, locker2.getLongTerm().getAvailableCapacity());
        assertEquals(80, locker2.getAvailableCapacity());
    }

    /**
     * Test the possibility the student understood the 50% rule is not defined by if
     * a single item have passed it, but by the total capacity all the items hold
     */
    @Test
    public void differentItemsBeyondFiftyPercentTest() {
        assertEquals(0, locker2.addItem(itemList[1], 5)); // hold 15 from capacity
        assertEquals(0, locker2.addItem(itemList[2], 3));// hold 15 from capacity
        assertEquals(0, locker2.addItem(itemList[3], 3)); // hold 30 from capacity
        assertEquals(0, locker2.addItem(itemList[0], 10));// hold 20 from capacity
        assertEquals(0, locker2.getLongTerm().curUsedStorage);
        // all items together hold 80% but the longTerm should be empty!
    }

    /**
     * Test what happen to the longTerm when adding chunks of same item to the locker
     */
    @Test
    public void addByChunksUntilPassedFiftyPercentTest() {
        assertEquals(0, locker2.addItem(itemList[2], 4));
        assertEquals(0, locker2.addItem(itemList[2], 4));
        assertEquals(8, locker2.getItemCount(itemList[2].getType()));
        assertEquals(1, locker2.addItem(itemList[2], 4));
        assertEquals(4, locker2.getItemCount(itemList[2].getType()));
        assertEquals(8, locker2.getLongTerm().getItemCount(itemList[2].getType()));
    }

    /**
     * Test adding to the locker more then 50% when both locker and longTerm have no storage
     */
    @Test
    public void addToLockerNoStorageInLongTermTest() {
        locker3.addItem(itemList[3], 60);
        locker3.addItem(itemList[3], 60);
        assertEquals(600, locker3.getAvailableCapacity());// twice into the locker
        assertEquals(0, locker3.getLongTerm().getAvailableCapacity());// all should go to longterm
        assertEquals(0, locker3.addItem(itemList[3], 10));
        assertEquals(300, locker3.getAvailableCapacity());
        assertEquals(0, locker3.getLongTerm().getAvailableCapacity());
        assertEquals(-1, locker3.addItem(itemList[3], 30));
        assertEquals(0, locker3.addItem(itemList[3], 20));
        assertEquals(0, locker3.getAvailableCapacity());
        assertEquals(-1, locker3.addItem(itemList[3], 1)); // both full

    }

    /**
     * Test removing 0 or negative numbers amounts of item from locker
     */
    @Test
    public void removeNonPositiveNumberTest() {
        addTenItemsOfNoContrast();
        assertEquals(-1, locker3.removeItem(itemList[0], -5));
        assertEquals(10, locker3.getItemCount(itemList[0].getType()));
        assertEquals(0, locker3.removeItem(itemList[1], 0));
        assertEquals(10, locker3.getItemCount(itemList[1].getType()));
    }

    /**
     * Test removing all items
     */
    @Test
    public void removeAllItemsTest() {
        addTenItemsOfNoContrast();
        assertEquals(0, locker3.removeItem(itemList[0], 5));
        assertEquals(0, locker3.removeItem(itemList[0], 5));
        assertEquals(0, locker3.removeItem(itemList[1], 5));
        assertEquals(0, locker3.removeItem(itemList[1], 5));
        assertEquals(0, locker3.removeItem(itemList[2], 5));
        assertEquals(0, locker3.removeItem(itemList[2], 5));
        assertEquals(0, locker3.removeItem(itemList[3], 5));
        assertEquals(0, locker3.removeItem(itemList[3], 5));

        assertEquals(1000, locker3.getAvailableCapacity());

    }

    /**
     * Test remove item that not in the locker
     */
    @Test
    public void removeNonExistItemTest() {
        locker3.addItem(itemList[0], 10);
        assertEquals(-1, locker3.removeItem(itemList[1], 1));
        assertEquals(-1, locker3.removeItem(itemList[2], 1));
    }

    /**
     * Test remove item amount that bigger then we have in the locker
     */
    @Test
    public void removeMoreThenPossibleTest() {
        addTenItemsOfNoContrast();
        assertEquals(-1, locker3.removeItem(itemList[0], 200));
        assertEquals(-1, locker3.removeItem(itemList[3], 11));

    }

    /**
     * helper method for the tests, adds 10 item from each kind(no contrast items allowed) to the locker
     */
    private void addTenItemsOfNoContrast() {
        locker3.addItem(itemList[0], 10);
        locker3.addItem(itemList[1], 10);
        locker3.addItem(itemList[2], 10);
        locker3.addItem(itemList[3], 10);
    }


}

