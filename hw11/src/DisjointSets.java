import java.util.Map;
import java.util.Set;

public class DisjointSets<T> implements DisjointSetsInterface<T> {
    //Should be a map of data to its parent; root data maps to itself.
    private Map<T, Node> set;

    /**
     * @param setItems the initial items (sameSet and merge will never be called
     * with items not in this set, this set will never contain null elements).
     */
    public DisjointSets(Set<T> setItems) {
        T[] arr = (T[]) (setItems.toArray());
        for (int i = 0; i < arr.length; i++) {
            set.put(arr[i], new Node(arr[i], 0));
        }
    }

    @Override
    public boolean sameSet(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }

        return false;
    }

    @Override
    public void merge(T u, T v) {
        Node uNode = set.get(u);
        Node vNode = set.get(v);
        if (uNode.getRank() <= vNode.getRank()) {
            uNode.setParent(vNode);
            vNode.setRank(vNode.getRank() + 1);
        } else if (vNode.getRank() < uNode.getRank()) {
            vNode.setParent(uNode);
            uNode.setRank(uNode.getRank() + 1);
        }
    }

    private class Node {
        //Fill in whatever methods or variables you believe are needed by node
        //here.  Should be O(1) space. This means no arrays, data structures,
        //etc.
        private T vertex;
        private Node parent;
        private int rank;

        public Node(T vertex, int rank) {
            this.vertex = vertex;
            this.parent = this;
            this.rank = rank;
        }

        public T getVertex() {
            return vertex;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
