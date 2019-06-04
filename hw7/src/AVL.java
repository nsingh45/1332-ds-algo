import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * My AVL implementation.
 *
 * @author Neha Singh
 */
public class AVL<T extends Comparable<T>> implements AVLInterface<T>,
       Gradable<T> {

    // Do not add additional instance variables
    private Node<T> root;
    private int size;

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        root = addHelp(root, data);
    }

    /**
     * Recursively finds the place to add the new data and performs rotations
     * as needed
     * @param n, a node to compare to the data
     * @param data, the data to add
     * @return the tree after rotations
     */
    private Node<T> addHelp(Node<T> n, T data) {
        if (n == null) {
            size++;
            return new Node<T>(data);
        }
        if (data.compareTo(n.getData()) < 0) {
            n.setLeft(this.addHelp(n.getLeft(), data));
        } else if (data.compareTo(n.getData()) > 0) {
            n.setRight(this.addHelp(n.getRight(), data));
        }
        n.setHeight(height(n));
        n.setBalanceFactor(balanceFactor(n));
        if (n.getBalanceFactor() > 1 || n.getBalanceFactor() < -1) {
            if (n.getBalanceFactor() < -1) {
                if (n.getRight() != null
                        && (n.getRight().getBalanceFactor()) < 0) {
                    n = rotateLeftLeft(n);
                } else if (n.getRight() != null
                        && (n.getRight().getBalanceFactor() > 0)) {
                    n = rotateLeftRight(n);
                }
            } else if (n.getBalanceFactor() > 1) {
                if (n.getLeft() != null
                        && (n.getLeft().getBalanceFactor() > 0)) {
                    n = rotateRightRight(n);
                } else if (n.getLeft() != null
                        && (n.getLeft().getBalanceFactor() < 0)) {
                    n = rotateRightLeft(n);
                }
            }
        }
        return n;
    }

    /**
     * Performs a right-right rotation on the tree
     * @param grandparent, the top node to be rotated
     * @return the rotated portion of the tree
     */
    private Node<T> rotateRightRight(Node<T> grandparent) {
        Node<T> parent = grandparent.getLeft();
        Node<T> b = parent.getRight();
        grandparent.setLeft(b);
        parent.setRight(grandparent);
        return parent;
    }

    /**
     * Performs a left-left rotation on the tree
     * @param grandparent, the top node to be rotated
     * @return the rotated portion of the tree
     */
    private Node<T> rotateLeftLeft(Node<T> grandparent) {
        Node<T> parent = grandparent.getRight();
        Node<T> b = parent.getLeft();
        grandparent.setRight(b);
        parent.setLeft(grandparent);
        return parent;
    }

    /**
     * Performs a left-right rotation on the tree
     * @param grandparent, the top node to be rotated
     * @return the rotated portion of the tree
     */
    private Node<T> rotateLeftRight(Node<T> grandparent) {
        Node<T> parent = grandparent.getRight();
        Node<T> child = parent.getLeft();
        Node<T> c = child.getRight();
        grandparent.setRight(child);
        parent.setLeft(c);
        child.setRight(parent);
        return (rotateLeftLeft(grandparent));
    }

    /**
     * Performs a right-left rotation on the tree
     * @param grandparent, the top node to be rotated
     * @return the rotated portion of the tree
     */
    private Node<T> rotateRightLeft(Node<T> grandparent) {
        Node<T> parent = grandparent.getLeft();
        Node<T> child = parent.getRight();
        Node<T> b = child.getLeft();
        grandparent.setLeft(child);
        parent.setRight(b);
        child.setLeft(parent);
        return (rotateRightRight(grandparent));
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node<T> toReturn = new Node<>(null);
        root = remove(root, data, toReturn);
        return toReturn.getData();
    }

    /**
     * Removes the given data and performs rotations as necessary
     * @param curr, the node to compare to the data
     * @param data, the data to remove
     * @param toReturn, a node containing the data in the node to remove
     * @return the updated and rotated tree
     */
    private Node<T> remove(Node<T> curr, T data, Node<T> toReturn) {
        if (curr == null) {
            return null;
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, toReturn));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, toReturn));
        } else {
            size--;
            toReturn.setData(curr.getData());

            if (curr.getLeft() != null && curr.getRight() != null) {
                curr.setData(twoChildren(curr));
            } else if (curr.getLeft() == null) {
                curr = curr.getRight();
            } else {
                curr = curr.getLeft();
            }
        }
        if (curr != null) {
            curr.setHeight(height(curr));
            curr.setBalanceFactor(balanceFactor(curr));
            if (curr.getBalanceFactor() > 1 || curr.getBalanceFactor() < -1) {
                if (curr.getBalanceFactor() < -1) {
                    if (curr.getRight() != null
                            && (curr.getRight().getBalanceFactor()) <= 0) {
                        curr = rotateLeftLeft(curr);
                    } else if (curr.getRight() != null
                            && (curr.getRight().getBalanceFactor() > 0)) {
                        curr = rotateLeftRight(curr);
                    }
                } else if (curr.getBalanceFactor() > 1) {
                    if (curr.getLeft() != null
                            && (curr.getLeft().getBalanceFactor() >= 0)) {
                        curr = rotateRightRight(curr);
                    } else if (curr.getLeft() != null
                            && (curr.getLeft().getBalanceFactor() < 0)) {
                        curr = rotateRightLeft(curr);
                    }
                }
            }
        }
        return curr;
    }

    /**
     * Removes a node with two children and replaces it with its predecessor
     * @param curr, the node to remove
     * @return the updated tree
     */
    private T twoChildren(Node<T> curr) {
        Node<T> pred = curr.getLeft();
        Node<T> predParent = null;
        while (pred.getRight() != null) {
            predParent = pred;
            pred = pred.getRight();
        }
        T ret = pred.getData();
        if (predParent == null) {
            curr.setLeft(pred.getLeft());
        } else {
            predParent.setRight(pred.getLeft());
        }
        return ret;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node<T> n = this.getHelp(root, data);
        return n.getData();
    }

    /**
     * Recursively finds the given data in the tree
     * @param n, a node to compare to the data
     * @param data, the given data
     * @return the found data or null if it is not in the tree
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
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> myList = new ArrayList<T>();
        preorder(root, myList);
        return myList;
    }

    /**
     * Traverses the tree in the order of a preorder search
     * @param n,
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
     * Traverses the tree in order of a postorder search
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
     * Recursively traverses the tree in the order of an inorder search
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
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return root.getHeight();
    }

    /**
     * Recursively finds the height of the given node
     * @param n
     * @return the height of the node
     */
    private int height(Node<T> n) {
        if (n == null) {
            return -1;
        }
        int left = height(n.getLeft());
        int right = height(n.getRight());
        if (left > right) {
            n.setHeight(left + 1);
            return left + 1;
        } else {
            n.setHeight(right + 1);
            return right + 1;
        }
    }

    /**
     * Finds and sets the balance factor of the given node
     * @param n
     * @return
     */
    private int balanceFactor(Node<T> n) {
        n.setBalanceFactor(height(n.getLeft()) - height(n.getRight()));
        return n.getBalanceFactor();
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }

}
