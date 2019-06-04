import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BST<T extends Comparable<T>> implements BSTInterface<T> {
    
    private Node<T> root = null;
    private int numItems = 0;
    
    @Override
    public void add(T data) {
        root = addHelp(root, data);
    }
    /**
     * Recursively adds data to the correct position in the tree
     * @param n
     * @param data
     * @return the modified tree
     */
    private Node<T> addHelp(Node<T> n, T data) {
        if (n == null) {
            numItems++;
            return new Node<T>(data);
        }
        if (data.compareTo(n.getData()) < 0) {
            n.setLeft(this.addHelp(n.getLeft(), data));
        } else if (data.compareTo(n.getData()) > 0) {
            n.setRight(this.addHelp(n.getRight(), data));
        }
        return n;
    }
    
    @Override
    public T remove(T data) {
        Node<T> replacement = removeHelp(root, data);
        Node<T> thisNode = getNode(data);
        if (root == null) {
            return null;
        }
        if (thisNode == root) {
            root = replacement;
            return thisNode.getData();
        }
        Node<T> parentNode = getParent(data);
        if (parentNode.getLeft() == thisNode) {
            parentNode.setLeft(replacement);
        } else if (parentNode.getRight() == thisNode) {
            parentNode.setRight(replacement);
        } else if (parentNode == thisNode) {
            root = replacement;
        }
        return thisNode.getData();
    }
    /**
     * Traverses the tree to find the correct position for the data
     * to be removed
     * @param n
     * @param data
     * @return the node to replace the data passed in
     */
    private Node<T> removeHelp(Node<T> n, T data) {
        if (n == null) {
            return null;
        }
        
        if (data.compareTo(n.getData()) == 0) {
            if (n.getLeft() == null && n.getRight() == null) {
                return null;
            } else if (n.getLeft() == null) {
                return n.getRight();
            } else if (n.getRight() == null) {
                return n.getLeft();
            } else {
                return getPredecessor(n);
            }
        }
        if (data.compareTo(n.getData()) < 0) {
            n.setLeft(removeHelp(n.getLeft(), data));
        }
        if (data.compareTo(n.getData()) > 0) {
            n.setRight(removeHelp(n.getRight(), data));
        }
        return n;
    }
    /**
     * Uses a helper to get a reference to a parent node
     * @param childData
     * @return a reference to the parent of the node containing childData
     */
    private Node<T> getParent(T childData) {
        return getParent(childData, root);
    }
    /**
     * Traverses the tree recursively to find a parent node for childData
     * @param childData
     * @param parent
     * @return the parent node, or null if there is none
     */
    private Node<T> getParent(T childData, Node<T> parent) {
        if (parent == null) {
            return null;
        }
        if (parent.getLeft() == null) {
            return null;
        }
        if (parent.getRight() == null) {
            return null;
        }
        if (childData.compareTo(parent.getData()) == 0) {
            return parent;
        }
        if ((childData.compareTo(parent.getRight().getData()) == 0) || (childData.compareTo(parent.getLeft().getData()) == 0)) {
            return parent;
        } else if (childData.compareTo(parent.getData()) < 0) {
            return getParent(childData, parent.getLeft());
        } else {
            return getParent(childData, parent.getRight());
        }
    }
    /**
     * Iteratively finds the predecessor node to the node passed in
     * @param n
     * @return a reference to n's predecessor
     */
    private Node<T> getPredecessor(Node<T> n) {
        Node<T> tracker = n.getLeft();
        while (tracker.getRight() != null) {
            tracker = tracker.getRight();
        }
        return tracker;
    }

    @Override
    public T get(T data) {
        Node<T> n = this.getHelp(root, data);
        if (n != null) {
            return n.getData();
        } else {
            return null;
        }
    }
    /**
     * Gets the node containing the data passed in
     * @param data
     * @return a reference to the node containing the parameter
     */
    private Node<T> getNode(T data) {
        return getHelp(root, data);
    }
    /**
     * Recursively traverses the tree to find the node containing the data
     * passed in as a parameter
     * @param n
     * @param data
     * @return a reference to the node containing the parameter
     */
    private Node<T> getHelp(Node<T> n, T data) {
        if (n == null) {
            return null;
        }
        if (data.compareTo(n.getData()) < 0) {
            n = getHelp(n.getLeft(), data);
        } else if (data.compareTo(n.getData()) > 0) {
            n = getHelp(n.getRight(), data);
        }
        return n;
    }

    @Override
    public boolean contains(T data) {
        return (this.get(data) != null);
    }

    @Override
    public int size() {
        return numItems;
    }

    @Override
    public List<T> preorder() {
        List<T> myList = new ArrayList<T>();
        preorder(root, myList);
        return myList;
    }
    /**
     * Recursively traverses the tree and adds items to the list as in order
     * of a preorder search
     * @param n
     * @param list
     */
    private void preorder(Node<T> n, List<T> list) {
        if (n == null) {
            return;
        }
        list.add(n.getData());
        preorder(n.getLeft(), list);
        preorder(n.getRight(), list);
    }

    @Override
    public List<T> postorder() {
        List<T> myList = new ArrayList<T>();
        postorder(root, myList);
        return myList;
    }
    /**
     * Recursively traverses the tree and adds items to the list as in order
     * of a postorder search
     * @param n
     * @param list
     */
    private void postorder(Node<T> n, List<T> list) {
        if (n == null) {
            return;
        }
        postorder(n.getLeft(), list);
        postorder(n.getRight(), list);
        list.add(n.getData());
    }

    @Override
    public List<T> inorder() {
        List<T> myList = new ArrayList<T>();
        inorder(root, myList);
        return myList;
    }
    /**
     * Recursively traverses the tree and adds items to the list as in order
     * of an inorder search
     * @param n
     * @param list
     */
    private void inorder(Node<T> n, List<T> list) {
        if (n == null) {
            return;
        }
        inorder(n.getLeft(), list);
        list.add(n.getData());
        inorder(n.getRight(), list);
    }

    @Override
    public List<T> levelorder() {
        Queue<Node<T>> q = new LinkedList<Node<T>>();
        List<T> myList = new ArrayList<T>();
        Node<T> nextNode = root;
        q.add(nextNode);
        while (!(q.isEmpty())) {
            nextNode = q.remove();
            myList.add(nextNode.getData());
            if (nextNode.getLeft() != null) {
                q.add(nextNode.getLeft());
            }
            if (nextNode.getRight() != null) {
                q.add(nextNode.getRight());
            }
        }
        return myList;
    }

    @Override
    public void clear() {
        root.setLeft(null);
        root.setRight(null);
        root = null;
        numItems = 0;
    }

    @Override
    public int height() {   //Height: Child node is 0, every one above that is 1
        return height(root);
    }
    
    private int height(Node<T> n) {
        if (n == null) {
            return -1;
        }
        int left = height(n.getLeft());
        int right = height(n.getRight());
        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }
    @Override
    public Node<T> getRoot() {
        return root;
    }
    
}