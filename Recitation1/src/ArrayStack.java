/**
 * ArrayStack
 *
 * Implementation of a stack using an array
 * as the backing store.
 *
 * @author Neha Singh
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {

    private static final int STACK_SIZE = 200;
    //TODO Add any private variables you may need.
    //HINT Use an object array and something to keep track
    //     of what the next available slot or last added index is.
    private int top;
    private T[] myStack;

    public ArrayStack() {
        //TODO Initialize an array of the size specified.
    	this.myStack = (T[]) (new Object[STACK_SIZE]);
    	this.top = 0;
    }

    @Override
    public void push(T t) {
		if (top >= STACK_SIZE) {
			throw new RuntimeException();
		} else if (t == null) {
			throw new IllegalArgumentException();
		} else {
			myStack[top] = t;
			top++;
		}
    }

    @Override
    public T pop() {
    	if (this.isEmpty()) {
    		return null;
    	} else {
    		top--;
    		return myStack[top];
    	}
    }

    @Override
    public T[] toArray() {
        return myStack;
    }

    @Override
    public boolean isEmpty() {
        return (top == 0);
    }
}
