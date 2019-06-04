import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Comprehensive test suite for HashMap.
 * @author Siddu Duddikunta
 */
public class AdditionalTests {

    /**
     * Le table.
     */
    private HashMap<Integer, String> table;

    /**
     * Creates a new table @Before each test.
     */
    @Before
    public void initTable() {
        table = new HashMap<>();
    }

    /**
     * Makes sure that size is 0 and that the backing array starts empty
     * with size 10.
     */
    @Test
    public void ensureThatMapBeginsEmpty() {
        assertEquals(0, table.size());
        assertArrayEquals(new MapEntry[10], table.toArray());
    }

    /**
     * Tests adding a pair to the map.
     */
    @Test
    public void testAdding() {
        MapEntry[] expected = new MapEntry[10];
        expected[7] = new MapEntry(17, "Hello!");

        assertNull(table.add(17, "Hello!"));
        assertEquals(1, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests adding lots of pairs to the map.
     */
    @Test
    public void testAddingMany() {
        addManyToTable();
    }

    /**
     * Tests that adding a null key throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullKey() {
        table.add(null, "Hi!");
    }

    /**
     * Tests that adding a null value throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullValue() {
        table.add(17, null);
    }

    /**
     * Tests that adding a null key and null value throws exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullKeyAndValue() {
        table.add(null, null);
    }

    /**
     * Tests that adding a duplicate key replaces values.
     */
    @Test
    public void testAddingDuplicateKey() {
        MapEntry[] expected = addManyToTable();
        expected[2] = new MapEntry(42, "To life, the universe, and everything!");

        assertEquals("I'm the answer.",
                table.add(42, "To life, the universe, and everything!"));
        assertEquals("To life, the universe, and everything!", table.get(42));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that adding a colliding mod hashcode works properly.
     */
    @Test
    public void testModdedKeyCollision() {
        MapEntry[] expected = new MapEntry[10];
        expected[2] = new MapEntry(42, "The answer.");
        expected[3] = new MapEntry(82, "Some other 2");

        assertNull(table.add(42, "The answer."));
        assertNull(table.add(82, "Some other 2"));
        assertEquals(2, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that adding pairs past the maximum load factor properly grows
     * the table.
     */
    @Test
    public void testAddingOverLoadFactor() {
        MapEntry[] expectedBefore = new MapEntry[10];
        expectedBefore[0] = new MapEntry(30, "30");
        expectedBefore[1] = new MapEntry(41, "41");
        expectedBefore[3] = new MapEntry(73, "73");
        expectedBefore[4] = new MapEntry(994, "994");
        expectedBefore[6] = new MapEntry(106, "106");
        expectedBefore[7] = new MapEntry(77, "77");

        MapEntry[] expectedAfter = new MapEntry[20];
        expectedAfter[10] = new MapEntry(30, "30");
        expectedAfter[1] = new MapEntry(41, "41");
        expectedAfter[13] = new MapEntry(73, "73");
        expectedAfter[14] = new MapEntry(994, "994");
        expectedAfter[6] = new MapEntry(106, "106");
        expectedAfter[17] = new MapEntry(77, "77");
        expectedAfter[9] = new MapEntry(89, "89");

        assertNull(table.add(30, "30"));
        assertNull(table.add(41, "41"));
        assertNull(table.add(73, "73"));
        assertNull(table.add(994, "994"));
        assertNull(table.add(106, "106"));
        assertNull(table.add(77, "77"));
        assertArrayEquals(expectedBefore, table.toArray());
        assertNull(table.add(89, "89"));
        assertEquals(7, table.size());
        assertArrayEquals(expectedAfter, table.toArray());
    }

    /**
     * Tests that removing from an empty table returns null.
     */
    @Test
    public void testRemovingFromAnEmptyTable() {
        assertNull(table.remove(0));
    }

    /**
     * Tests removing an element from the table.
     */
    @Test
    public void testRemoving() {
        MapEntry[] expected = addManyToTable();

        assertEquals("I'm the answer.", table.remove(42));
        assertEquals(3, table.size());
        expected[2].setRemoved(true);
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests adding a key into a position that was previously removed.
     */
    @Test
    public void testAddingElementInRemovedPosition() {
        MapEntry[] expected = addManyToTable();

        assertEquals("I'm the answer.", table.remove(42));
        assertEquals(3, table.size());
        assertNull(table.add(42, "I'm the answer."));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that removing a key that does not exist works.
     */
    @Test
    public void testRemovingAKeyThatDoesNotExist() {
        MapEntry[] expected = addManyToTable();
        assertNull(table.remove(43));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that get on an empty table returns null.
     */
    @Test
    public void testGetOnEmptyTable() {
        assertNull(table.get(0));
    }

    /**
     * Tests that get works.
     */
    @Test
    public void testGetting() {
        MapEntry[] expected = addManyToTable();

        assertEquals("I'm the answer.", table.get(42));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that getting a key that doesn't exist returns null.
     */
    @Test
    public void testGettingAKeyThatDoesNotExist() {
        MapEntry[] expected = addManyToTable();
        assertNull(table.get(43));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that getting a nonexistent key on a table that has a filled
     * backing array (with all but one removed pairs) doesn't result in
     * an infinite loop.
     */
    @Test(timeout = 200)
    public void testGettingOnTableWithNoNulls() {
        testAddingOnTableWithNoNulls();
        assertNull(table.get(19));
    }

    /**
     * Tests that removing a nonexistent key on a table that has a filled
     * backing array (with all but one removed pairs) doesn't result in
     * an infinite loop.
     */
    @Test(timeout = 200)
    public void testRemovingOnTableWithNoNulls() {
        testAddingOnTableWithNoNulls();
        assertNull(table.remove(19));
    }

    /**
     * Tests that adding to a table with all removed pairs doesn't result in
     * a grown table.
     */
    @Test(timeout = 200)
    public void testAddingOnTableWithNoNulls() {
        MapEntry[] expected = createFullBackingArrayTable();
        assertArrayEquals(expected, table.toArray());
        assertNull(table.add(42, "Hi!"));
        assertEquals(1, table.size());
        expected[2] = new MapEntry(42, "Hi!");
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests to ensure that decrementing size doesn't occur when nothing
     * is to be removed.
     */
    @Test
    public void testThatRemovingPastZeroDoesNotDecrementSize() {
        table.remove(2);
        table.remove(3);
        table.remove(6);
        table.remove(7);
        assertEquals(0, table.size());
    }

    /**
     * Tests that clear works properly.
     */
    @Test
    public void testClear() {
        addManyToTable();

        table.clear();
        assertEquals(0, table.size());
        assertArrayEquals(new MapEntry[10], table.toArray());
    }

    /**
     * Tests that clear on a map that was grown still works properly.
     */
    @Test
    public void testClearOnABigMap() {
        testAddingOverLoadFactor();

        table.clear();
        assertEquals(0, table.size());
        assertArrayEquals(new MapEntry[10], table.toArray());
    }

    /**
     * Tests that your map handles negative hashcodes properly.
     */
    @Test
    public void testNegativeHashCodes() {
        MapEntry[] expected = addManyToTable();
        assertEquals(null, table.get(-42));
        expected[3] = new MapEntry(-42, "negative");
        assertNull(table.add(-42, "negative"));
        assertEquals(5, table.size());
        assertArrayEquals(expected, table.toArray());
    }

    /**
     * Tests that keySet works properly.
     */
    @Test
    public void testKeySet() {
        addManyToTable();
        Set<Integer> keySet = table.keySet();
        assertEquals(4, keySet.size());
        assertTrue(keySet.contains(42));
        assertTrue(keySet.contains(104));
        assertTrue(keySet.contains(17));
        assertTrue(keySet.contains(99));
    }

    /**
     * Tests that values works properly.
     */
    @Test
    public void testValues() {
        addManyToTable();
        List<String> values = table.values();
        assertEquals(4, values.size());
        assertEquals("I'm the answer.", values.get(0));
        assertEquals("I'm 104!", values.get(1));
        assertEquals("Hello!", values.get(2));
        assertEquals("99!", values.get(3));
    }

    /**
     * Tests that contains works properly.
     */
    @Test
    public void testContains() {
        addManyToTable();
        assertTrue(table.contains(42));
        assertTrue(table.contains(104));
        assertTrue(table.contains(17));
        assertTrue(table.contains(99));
        assertFalse(table.contains(52));
    }

    private MapEntry[] addManyToTable() {
        MapEntry[] expected = new MapEntry[10];
        expected[2] = new MapEntry(42, "I'm the answer.");
        expected[4] = new MapEntry(104, "I'm 104!");
        expected[7] = new MapEntry(17, "Hello!");
        expected[9] = new MapEntry(99, "99!");

        assertNull(table.add(17, "Hello!"));
        assertNull(table.add(99, "99!"));
        assertNull(table.add(42, "I'm the answer."));
        assertNull(table.add(104, "I'm 104!"));
        assertEquals(4, table.size());
        assertArrayEquals(expected, table.toArray());

        return expected;
    }

    private MapEntry[] createFullBackingArrayTable() {
        MapEntry<Integer, String>[] expected = new MapEntry[10];
        expected[0] = new MapEntry(0, "0");
        expected[1] = new MapEntry(1, "1");
        expected[2] = new MapEntry(2, "2");
        expected[3] = new MapEntry(3, "3");
        expected[4] = new MapEntry(4, "4");
        expected[5] = new MapEntry(5, "5");
        expected[6] = new MapEntry(6, "6");
        expected[7] = new MapEntry(7, "7");
        expected[8] = new MapEntry(8, "8");
        expected[9] = new MapEntry(9, "9");
        for (MapEntry<Integer, String> entry : expected) {
            entry.setRemoved(true);
            table.add(entry.getKey(), entry.getValue());
            table.remove(entry.getKey());
        }
        return expected;
    }
}
