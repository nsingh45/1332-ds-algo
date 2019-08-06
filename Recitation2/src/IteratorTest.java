import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class IteratorTest {
    
    private CircularLinkedList<Integer> list;
    private Iterator<Integer> fib;
    
    @Before
    public void setUp() {
        list = new CircularLinkedList<Integer>();
        fib = new FibonacciIterator(5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test (timeout = 200)
    public void testLinkedList() {
        list.addToBack(new Integer(0));
        list.addToBack(new Integer(1));
        list.addToBack(new Integer(2));
        list.addToBack(new Integer(3));
        list.addToBack(new Integer(4));
        Integer[] itemsArr = new Integer[5];
        int counter = 0;
        for (Integer i : list) {
            itemsArr[counter] = i;
            counter++;
        }
        assertEquals(list.toList(), itemsArr);
        System.out.println(itemsArr);
    }

}
