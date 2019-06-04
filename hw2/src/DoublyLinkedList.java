/**
 * Doubly linked list implementation
 * @author Neha Singh
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int numItems;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            this.addToFront(data);
        } else if (index == this.size()) {
            this.addToBack(data);
        } else {
            Node<T> prevNode = head;
            Node<T> thisNode = new Node<T>(data);
            for (int i = 0; i < index - 1; i++) {
                prevNode = prevNode.getNext();
            }
            Node<T> nextNode = prevNode.getNext();
            prevNode.setNext(thisNode);
            thisNode.setPrevious(prevNode);
            thisNode.setNext(nextNode);
            nextNode.setPrevious(thisNode);
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
        } else if (index == this.size() - 1) {
            return this.removeFromBack();
        } else {
            Node<T> prevNode = head;
            for (int i = 0; i < index - 1; i++) {
                prevNode = prevNode.getNext();
            }
            T data = (prevNode.getNext()).getData();
            Node<T> nextNode = (prevNode.getNext()).getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrevious(prevNode);
            numItems--;
            return data;
        }
    }

    @Override
    public void addToFront(T t) {
        Node<T> newHead = new Node<T>(t);
        if (this.isEmpty()) {
            newHead.setPrevious(null);
            newHead.setNext(null);
            head = newHead;
            tail = newHead;
        } else {
            newHead.setNext(head);
            head.setPrevious(newHead);
            head = newHead;
            head.setPrevious(null);
        }
        numItems++;
    }

    @Override
    public void addToBack(T t) {
        Node<T> newTail = new Node<T>(t);
        if (this.isEmpty()) {
            newTail.setNext(null);
            newTail.setPrevious(null);
            tail = newTail;
            head = tail;
        } else {
            newTail.setPrevious(tail);
            tail.setNext(newTail);
            newTail.setNext(null);
            tail = newTail;
        }
        numItems++;
    }

    @Override
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        }
        T data = head.getData();
        if (numItems == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        numItems--;
        return data;
    }

    @Override
    public T removeFromBack() {
        if (this.isEmpty()) {
            return null;
        }
        T data = tail.getData();
        if (numItems == 1) {
            tail = null;
            head = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        numItems--;
        return data;
    }

    @Override
    public Object[] toArray() {
        Object[] myArr = new Object[this.size()];
        for (int i = 0; i < this.size(); i++) {
            myArr[i] = this.get(i);
        }
        return myArr;
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
        head.setNext(null);
        head = null;
        tail.setPrevious(null);
        tail = null;
        numItems = 0;
    }


    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
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
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }
}
