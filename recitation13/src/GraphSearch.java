import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** 
 * Graph Search
 * Recitation 13
 * Remember - if you ask a question on Piazza,
 * make sure it's private!
 * 
 * Also, you do not have to run CheckStyle on this assignment.
 * But make it look pretty.  Pretty please. With a cherry on top.
 * 
 * If you don't know what to return for something, look at the JUnits.
 * They should tell you what they expect if null is passed in, etc.
 * If there's not a test case for it, then don't worry about it! :)
 * 
 * @author Neha Singh
 */
public class GraphSearch {

	/**
	 * Searches the Graph passed in as an AdjcencyList(adjList) to find if a path exists from the start node to the goal node
	 * using General Graph Search.
	 *
	 * Assume the AdjacencyList contains adjacent nodes of each node in the order they should be added to the Structure.
	 *
	 * The structure(struct) passed in is an empty structure may behave as a Stack or Queue and the
	 * correspondingly search function should execute DFS(Stack) or BFS(Queue) on the graph.
	 *
	 * We've written the stack and queue for you.  Your mission, should you choose to accept it (and you should),
	 * is to finish the graph search algorithm.
	 * 
	 * @param start
	 * @param struct
	 * @param adjList
	 * @param goal
	 * @return true if path exists false otherwise
	 */
	public static <T> boolean generalGraphSearch(T start, Structure<T> struct, Map<T, List<T>> adjList, T goal) {
	    struct.add(start);
	    LinkedList<T> visited = new LinkedList<T>();
	    boolean result = false;
	    boolean terminator = true;
	    do {
	        if (struct.isEmpty()) {
	            result = false;
	            terminator = false;
	        } else {
    	        T node = struct.remove();
    	        if (node.equals(goal)) {
    	            result = true;
    	            terminator = false;
    	        }
    	        for (T child : adjList.get(node)) {
    	            if (!visited.contains(child)) {
    	                struct.add(child);
    	            }
    	        }
                visited.add(node);
	        }
	    } while (terminator);
		return result;
	}
	
	/**
	 * Searches the Graph passed in as an AdjcencyList(adjList) to find if a path exists from the start node to the goal node
	 * using Bredth First Search.
	 *
	 * Assume the AdjacencyList contains adjacent nodes of each node in the order they should be added to the Structure.
	 *
	 * @param start
	 * @param adjList
	 * @param goal
	 * @return true if path exists, false otherwise
	 */
	public static <T> boolean breadthFirstSearch(T start, Map<T, List<T>> adjList, T goal) {
		if (adjList == null) {
		    throw new IllegalArgumentException();
		}
	    StructureStack<T> myStack = new StructureStack<T>();
        return generalGraphSearch(start, myStack, adjList, goal);
	}
	
	/**
	 * Searches the Graph passed in as an AdjcencyList(adjList) to find if a path exists from the start node to the goal node
	 * using Depth First Search.
	 *
	 * Assume the AdjacencyList contains adjacent nodes of each node in the order they should be added to the Structure.
	 *
	 * @param start
	 * @param adjList
	 * @param goal
	 * @return true if path exists, false otherwise
	 */
	public static <T> boolean depthFirstSearch(T start, Map<T, List<T>> adjList, T goal) {
		if (adjList == null) {
		    throw new IllegalArgumentException();
		}
	    StructureQueue<T> myQueue = new StructureQueue<T>();
	    return generalGraphSearch(start, myQueue, adjList, goal);
	}
	
} // There's only one more recitation left! :)
