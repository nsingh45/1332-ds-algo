import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class MST {

    /**
     * Using disjoint set(s), run Kruskal's algorithm on the given graph and
     * return the MST. Return null if no MST exists for the graph.
     *
     * @param g The graph to be processed. Will never be null.
     * @return The MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> kruskals(Graph g) {
        // TODO
        return null;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree. If no MST exists, return null.
     *
     * @param g The graph to be processed. Will never be null.
     * @param start The ID of the start node. Will always exist in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> prims(Graph g, int start) {
        Vertex v = new Vertex(start);
        ArrayList<Edge> mst = new ArrayList<Edge>();
        ArrayList<Vertex> visited = new ArrayList<Vertex>();
        Stack<Vertex> myStack = new Stack<Vertex>();
        visited.add(v);
        myStack.add(v);
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
        Map<Vertex, Integer> adjList = g.getAdjacencies(v);
        Set<Vertex> set = adjList.keySet();
        while (mst.size() < g.getVertices().size() - 1) {
            for (Vertex vert : set) {
                if (!visited.contains(vert)) {
                    pq.add(new Edge(v, vert, adjList.get(vert)));
                }
            }
            if (!pq.isEmpty()) {
                Edge nextEdge = pq.remove();
                if (!myStack.contains(nextEdge.getV())) {
                    mst.add(nextEdge);
                    myStack.add(nextEdge.getV());
                    visited.add(v);
                } else {
                    v = myStack.pop();
                }

                v = nextEdge.getV();
            } //else {
               // v = myStack.pop();
            //}
            adjList = g.getAdjacencies(v);
            set = adjList.keySet();
        }
        return mst;
    }
}
