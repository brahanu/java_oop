import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LongTermTest {
    private LongTermStorage longTerm1;
    private LongTermStorage longTerm2;

    private Item[] itemList = ItemFactory.createAllLegalItems();


    @Before
    public void createStorage() {

        longTerm1 = new LongTermStorage();
        longTerm2 = new LongTermStorage();

    }

    /**
     * Test if the Init working, meaning check if the the constructor creating a locker
     */
    @Test
    public void longTermInitializeTest() {
        longTerm1 = new LongTermStorage();
        assertNotNull(longTerm1);
    }

    /**
     * Test longTerm getCapcity, expected 1000
     */
    @Test
    public void longTermGetCapacityTest() {
        assertEquals(1000, longTerm1.getCapacity());
        assertEquals(1000, longTerm2.getCapacity());
    }

    /**
     * Test getAvailableCapacity,
     * 1: adds item untill storage full, expected 0
     * 2: adds 200 helmet, size 1, expected 400
     */
    @Test
    public void longTermGetAvailableCapacity() {
        assertEquals(0, longTerm1.addItem(itemList[3], 100));
        assertEquals(0, longTerm2.addItem(itemList[1], 200));
        assertEquals(0, longTerm1.getAvailableCapacity());
        assertEquals(400, longTerm2.getAvailableCapacity());
    }

    /**
     * Test getItemCount, 3 test
     * 1: adds zero amount of helmet, size 1, check the amount after the addition, expected 0
     * 2: check the itemcount for not existing item,expected 0
     * 3: regular check, adds 15 of helmet, size 3, and check the itemcount expected 15
     */
    @Test
    public void longTermGetItemCountTest() {
        // adds some items into the lockers
        assertEquals(0, longTerm1.addItem(itemList[1], 0)); // adds zero items into the locker
        assertEquals(0, longTerm1.getItemCount(itemList[1].getType()));
        assertEquals(0, longTerm2.addItem(itemList[1], 3));
        assertEquals(0, longTerm2.getItemCount("00!@#")); //try to get item count for false type
        assertEquals(0, longTerm2.addItem(itemList[2], 15));
        assertEquals(15, longTerm2.getItemCount(itemList[2].getType()));

    }

    /**
     * Test if the Hashmap that represent the locker can be edited from outside
     * hashmap is immutable object if the coder returnerd the orginial one i should be able to change it not thru
     * the method add/remove/reset
     */
    @Test
    public void longTermGetInventoryTest() {
        LongTermStorage storage = new LongTermStorage();
        HashMap<String, Integer> checkMap = storage.getInventory();
        storage.addItem(itemList[0], 3);
        assertTrue("getInventory Test Failed: check if the inventory updated after addition",
                storage.getInventory().containsKey(itemList[0].getType()));
        assertEquals("getInventory Test Failed: check if the inventory updated after addition",
                3, storage.getItemCount(itemList[0].getType()));
        checkMap.put("blabla", 5);
        assertEquals("getInventory Test Failed: I Have access to the original inventory Hashmap",
                0, storage.getItemCount("blabla"));
    }

    @Test
    public void resetInventoryTest() {
        assertEquals(0, longTerm1.addItem(itemList[3], 100));
        assertEquals(0, longTerm1.getAvailableCapacity());
        longTerm1.resetInventory();
        assertEquals(1000, longTerm1.getAvailableCapacity());
        assertEquals(0, longTerm1.getItemCount(itemList[3].getType()));


    }

    // general scenarios

    /**
     * Test adding 0 items into the locker; Test adding negative number of items;
     */
    @Test
    public void longTermAddNonPositiveQuantity() {
        longTerm1.addItem(itemList[1], 0);
        longTerm1.addItem(itemList[2], -1);
        assertTrue(longTerm1.getInventory().containsKey(itemList[1].getType()));
        assertEquals(0, longTerm1.getItemCount(itemList[0].getType()));
        assertEquals(1000, longTerm1.getAvailableCapacity());
        assertEquals(0, longTerm1.getItemCount(itemList[1].getType()));

    }

    /**
     * Test adding contrast Item to the longTerm, expect Success meaning addItem will return 0
     */
    @Test
    public void addContrastItemTest() {
        assertEquals(0, longTerm1.addItem(itemList[0], 10));
        assertEquals(0, longTerm1.addItem(itemList[4], 10));

    }

    /**
     * Test adding items until we passed the capacity,Expect Fail adding beyond the capacity
     */
    @Test
    public void addItemUntillMoreThenCapacity() {
        assertEquals(0, longTerm1.addItem(itemList[0], 20));
        assertEquals(0, longTerm1.addItem(itemList[1], 20));
        assertEquals(0, longTerm1.addItem(itemList[2], 20));
        assertEquals(0, longTerm1.addItem(itemList[3], 20));
        assertEquals(0, longTerm1.addItem(itemList[4], 20));
        assertEquals(-1, longTerm1.addItem(itemList[3], 55));


    }
}

