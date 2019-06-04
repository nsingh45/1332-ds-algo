/**
 * Created by osama on 11/26/14.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class OsamaTest {
    private Graph disconnectedGraph;

    private void setGraph1() {
        /*
           D     A
          /    /   \
        /     C - - B
       E

       E(A, B) = 4
       E(A, C) = 1
       E(B, C) = 6
       E(D, E) = 2
         */
        String totalEdges = "4 ";
        String first = "1 2 4 ";
        String second = "1 3 1 ";
        String third = "2 3 6 ";
        String fourth = "4 5 2";
        String input = totalEdges + first + second + third + fourth;
        disconnectedGraph = new Graph(input);
    }

    private void setGraph2() {
        /*
                    A
                  / |    D ----- E
                 /  |            |
                B - |- - G       |
                 \  |  /         F
                  \ | /
                    C


       E(A, B) = 4
       E(A, C) = 1
       E(B, C) = 6
       E(D, E) = 2
       E(E, F) = 11
       E(B, G) = 22
       E(C, G) = 3
         */
        String totalEdges = "7 ";
        String first = "1 2 4 ";
        String second = "1 3 1 ";
        String third = "2 3 6 ";
        String fourth = "4 5 2 ";
        String fifth = "5 6 11 ";
        String sixth = "2 7 22 ";
        String seventh = "3 7 3 ";
        String input = totalEdges + first + second + third + fourth + fifth + sixth + seventh;
        disconnectedGraph = new Graph(input);
    }

    @Test(timeout=200)
    public void InvalidMSTPrims1() {
        setGraph1();
//        Start at A
        Collection<Edge> resultMST = MST.prims(disconnectedGraph, 1);
        assertNull(resultMST);

//        Start at D
        resultMST = MST.prims(disconnectedGraph, 4);
        assertNull(resultMST);
    }

    @Test(timeout=200)
    public void InvalidMSTPrims2() {
        setGraph2();
//        Start at A
        Collection<Edge> resultMST = MST.prims(disconnectedGraph, 1);
        assertNull(resultMST);

//        Start at F
        resultMST = MST.prims(disconnectedGraph, 6);
        assertNull(resultMST);
    }

    @Test(timeout=200)
    public void InvalidMSTKruskals1() {
        setGraph1();
        Collection<Edge> resultMST = MST.kruskals(disconnectedGraph);
        assertNull(resultMST);
    }

    @Test(timeout=200)
    public void InvalidMSTKruskals2() {
        setGraph2();
        Collection<Edge> resultMST = MST.kruskals(disconnectedGraph);
        assertNull(resultMST);
    }

}