package pathfinder.implTest;

import org.junit.Before;
import org.junit.Test;
import pathfinder.ModelConnector;
import pathfinder.SearchPath;
import pathfinder.datastructures.*;
import graph.*;

import static org.junit.Assert.assertEquals;

/**
 * this class test the implementation of the SearchPath class and its Dijkstra's Algorithm
 */
public class SearchPathTest {
    private Graph<String, Double> graph;

    @Before
    public void SetUp(){
        graph = new Graph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addChild("A", "B", 1.0);
        graph.addChild("A", "C", 1.0);
        graph.addChild("B", "C", 1.0);
    }

    @Test
    public void testSearch(){
        Path<String> result = SearchPath.findShortestPath("A", "C", graph);
        assertEquals(0, Double.compare(1.0, result.getCost()));
        assertEquals("C", result.getEnd());
        assertEquals("A", result.getStart());
    }
}
