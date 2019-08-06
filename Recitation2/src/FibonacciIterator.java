import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a class that will allow you to iterate through the first n
 * Fibonacci elements
 * @author kushagramansingh
 *
 */
public class FibonacciIterator implements Iterator<Integer> {
	private Integer n;
	private Integer current;
	private Integer runningValue = 1;
	private Integer previousValue = 0;
	
	public FibonacciIterator(Integer n) {
	    this.n = n;
	    current = 1;
	}
	
	@Override
	public boolean hasNext() {
		return (current < n);
	}

	@Override
	public Integer next() {
	    if (hasNext()) {
            int placeholder = runningValue;
            runningValue += previousValue;
            previousValue = placeholder;
            current++;
            return runningValue;
	    } else {
	        throw new NoSuchElementException();
	    }
	}

    @Override
    public void remove() {
        // TODO Auto-generated method stub
        
    }
}
