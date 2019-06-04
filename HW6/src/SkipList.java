import java.util.HashSet;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;


    /**
     * constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = new CoinFlipper();
        this.size = 0;
        this.head = new Node<T>(null, 0);   //level starts at 0
    }

    @Override
    public T first() {
        if (size() == 0) {
            return null;
        } else {
            int level = head.getLevel();
            Node<T> tracker = head;
            for (int i = level; i > 0; i--) {
                tracker = tracker.getDown();
            }
            return (tracker.getNext()).getData();
        }
    }

    @Override
    public T last() {
        if (size() == 0) {
            return null;
        } else {
            int level = head.getLevel();
            Node<T> tracker = head;
            for (int i = level; i > 0; i--) {
                tracker = tracker.getDown();
            }
            while (tracker.getNext() != null) {
                tracker = tracker.getNext();
            }
            return tracker.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        return (recursiveGet(data, head) == null);
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        int level = determineLevel();
        int headLevel = head.getLevel();
        if (level > headLevel) {
            for (int i = headLevel + 1; i <= level; i++) {
                Node<T> above = new Node<T>(null, i);
                above.setDown(head);
                head.setUp(above);
                head = above;
            }

        }
        Node<T> newData = new Node<T>(data, level);
        Node<T> nextData = newData;
        for (int i = level - 1; i >= 0; i--) {
            Node<T> dataUnder = new Node<T>(data, i);
            dataUnder.setUp(nextData);
            nextData.setDown(dataUnder);
            nextData = dataUnder;
        }
        Node<T> tracker = head;
        for (int i = 0; i < tracker.getLevel() - level; i++) {
            tracker = tracker.getDown();
        }
        while ((tracker.getNext() != null)
                && (((tracker.getNext()).getData()).compareTo(data)) < 0) {
            tracker = tracker.getNext();
        }
        int trackLevel = tracker.getLevel();
        for (int i = trackLevel; i >= 0; i--) {
            nextData = tracker.getNext();
            tracker.setNext(newData);
            newData.setNext(nextData);
            tracker = tracker.getDown();
            tracker = getNewTracker(data, tracker);
            if (tracker == null) {
                i = 0;
            }
        }
        size++;

    }
    /**
     * Gets next largest node before the node passed in
     * @param data, the data to be compared
     * @param tracker, the node
     * @return the next largest node on the same level
     */
    private Node<T> getNewTracker(T data, Node<T> tracker) {
        if (tracker == null) {  //can't compare null to data
            return null;
        } else if (tracker.getNext() == null) {
            return tracker;
        } else if (tracker.getNext() != null
                && ((tracker.getNext()).getData()).compareTo(data) > 0) {
            return tracker;
        } else if (tracker.getNext() != null
                && ((tracker.getNext()).getData()).compareTo(data) == 0) {
            return tracker.getNext();
        } else {
            while ((tracker.getNext() != null)
                    && (((tracker.getNext()).getData()).compareTo(data)) < 0) {
                tracker = tracker.getNext();
            }
            return tracker;
        }
    }
    /**
     * Flips a coin to return the level new data should be stored in
     * @return an int determining the level of new data
     */
    private int determineLevel() {
        CoinFlipper.Coin c = coinFlipper.flipCoin();
        int level = 0;
        while (c == CoinFlipper.Coin.HEADS) {
            c = coinFlipper.flipCoin();
            level++;
        }
        return level;
    }

    @Override
    public T get(T data) {
        //Node<T> tracker = head;
        if (size() == 0) {
            return null;
        } else {
            /*(for (int i = tracker.getLevel(); i > 0; i--) {
                System.out.println(toString());
                tracker = getNewTracker(data, tracker);
                if ((tracker.getData() != null)
                && (tracker.getData()).compareTo(data) == 0) {
                    i = 0;
                } else {
                    tracker = tracker.getDown();
                }
            }
        }
        if (!(tracker.getData().equals(data))) {
            return null;
        } else {
            return tracker.getData();
        } */
            return (recursiveGet(data, head).getData());
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.head = new Node<T>(null, 0);
    }

    @Override
    public Set<T> dataSet() {
        HashSet<T> mySet = new HashSet<T>();
        Node<T> tracker = recursiveHelper(head);
        while (tracker.getNext() != null) {
            mySet.add(tracker.getData());
        }
        return mySet;
    }
    /**
     * gets the first node on the lowest level of the structure
     * @param n, the node to find the lowest level of
     * @return the lowest node
     */
    private Node<T> recursiveHelper(Node<T> n) {
        if (n.getDown() == null) {
            return n;
        } else {
            return recursiveHelper(n.getDown());
        }
    }

    @Override
    public T remove(T data) {
        return null;
    }

    public String toString() {
        String str = "";
        Node<T> n = head;
        for (int i = n.getLevel(); i >= 0; i--) {
            str += "Level " + i + ": " + debugString(n) + "\n";
            n = n.getDown();
        }
        return str;
    }
    /**
     * Recursively finds a node in the SkipList
     * @param data, the data for the node to be found
     * @param n, the node to be found
     * @return a reference to the node to be found
     */
    private Node<T> recursiveGet(T data, Node<T> n) {
        if (n == null) {
            return null;
        } else if (n.getDown() == null) {
            return n;
        } else if (((n.getNext()).getData()).compareTo(data) > 0) {
            return recursiveGet(data, n.getDown());
        } else if (((n.getNext()).getData()).compareTo(data) < 0) {
            return recursiveGet(data, n.getNext());
        } else if ((((n.getNext()).getData()).compareTo(data) == 0)) {
            return n.getNext();
        }
        return n;
    }
    /**
     * Helper for toString()
     * @param n
     * @return
     */
    private String debugString(Node<T> n) {
        if (n == null) {
            return "";
        } else {
            return n.getData() + " " + debugString(n.getNext());
        }
    }
}
