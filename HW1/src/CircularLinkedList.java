
/**
 * CircularLinkedList implementation
 * @author Neha Singh
 * @version 1.0
 */
public class CircularLinkedList<T> implements LinkedListInterface<T> {

	private int numItems = 0;
	private Node<T> head = null;
	private Node<T> tail = null;
	
    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > this.size()) {
        	throw new IndexOutOfBoundsException();
        } else if (index == 0) {
        	this.addToFront(data);
        } else if (index == this.size()) {
        	this.addToBack(data);
        } else {
        	Node<T> placeholder = head;
        	Node<T> thisNode = new Node<T>(data);
        	for (int i = 0; i < index - 1; i++) {
        		placeholder = placeholder.getNext();
        	}
        	thisNode.setNext(placeholder.getNext());
        	placeholder.setNext(thisNode);
        	numItems++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size()) {
        	throw new IndexOutOfBoundsException();
        } else if (index == 0) {
        	return head.getData();
        } else if (index == this.size() - 1) {
        	return tail.getData();
        } else {
        	Node<T> placeholder = head;
        	for (int i = 0; i < index; i++) {
        		placeholder = placeholder.getNext();
        	}
        	return placeholder.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= this.size()) {
        	throw new IndexOutOfBoundsException();
        } else if (index == 0) {
        	return this.removeFromFront();
        } else {
        	Node<T> placeholder = head;
        	for (int i = 0; i < index - 1; i++) {
        		placeholder = placeholder.getNext();
        	}
        	T data = (placeholder.getNext()).getData();
        	placeholder.setNext((placeholder.getNext()).getNext());
        	numItems--;
        	return data;
        }
    }

    @Override
    public void addToFront(T t) {
        Node<T> frontNode = new Node<T>(t, head);
        head = frontNode;
        numItems++;
        if (numItems == 1) {
        	head.setNext(head);
        	tail = head;
        }
        tail.setNext(head);
    }

    @Override
    public void addToBack(T t) {
        Node<T> backNode = new Node<T>(t, head);
        numItems++;
        if (numItems == 1) {
        	head = backNode;
        	tail = backNode;
        }
        tail.setNext(backNode);
        tail = backNode;
    }

    @Override
    public T removeFromFront() {
        T data = head.getData();
        head = head.getNext();
        tail.setNext(head);
        numItems--;
        return data;
    }

    @Override
    public T removeFromBack() {
    	if (numItems == 0) {
    		return null;
    	}
        Node<T> n = head;
        while (n.getNext() != tail) {
        	n = n.getNext();
        }
        T data = (n.getNext()).getData();
        n.setNext(head);
        tail = n;
        numItems--;
        return data;
    }

    @Override
    public T[] toList() {
        T[] myList = (T[]) (new Object[this.size()]);
        for (int i = 0; i < this.size(); i++) {
        	myList[i] = this.get(i);
        }
        return myList;
    }

    @Override
    public boolean isEmpty() {
        return numItems == 0;
    }

    @Override
    public int size() {
        return numItems;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        numItems = 0;
    }

    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * @return Node representing the head of the linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Reference to the tail node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * This method is for your testing purposes.
     * You may choose to implement it if you wish.
     */
    @Override
    public String toString() {
        return "";
    }
}

