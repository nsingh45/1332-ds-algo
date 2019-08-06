/**t>
 * StacksQueue
 *
 * Implementation of a queue using
 * two stacks as the backing store.
 *
 * @author Neha Singh
 * @version 1.0
 */
public class StacksQueue<T> implements QueueInterface<T> {

    // TODO Add any private variables you need.
    // HINT Use one stack as the "enqueue" stack
    //      and one stack as the "dequeue" stack
	//one stack in forward, one in reverse
	private ArrayStack<T> addingStack;
	private ArrayStack<T> removingStack;
	
	public StacksQueue() {
		this.addingStack = new ArrayStack();
		this.removingStack = new ArrayStack();
	}
	
    @Override
    public void enqueue(Object o) {
    	addingStack.push((T)o);
    }

    @Override
    public T dequeue() {
    	if (this.isEmpty()) {
    		return null;
    	} else {
    		while (!addingStack.isEmpty()) {
    			removingStack.push(addingStack.pop());
    		}
    		T data = removingStack.pop();
    		while (!removingStack.isEmpty()) {
    			addingStack.push(removingStack.pop());
    		}
    		return data;
    	}
    }

    @Override
    public boolean isEmpty() {
        return addingStack.isEmpty();
    }
}
