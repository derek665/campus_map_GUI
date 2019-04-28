package graph.implTest;

import static org.junit.Assert.*;
import java.util.*;
import graph.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * This class contains a set of test cases that can be used to test the implementation of Graph class
 */

public final class GraphTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph graph1;
    private Graph graph2;
    private Graph graph3;

    @Before
    public void setUp() {
        graph1 = new Graph();
        graph2 = new Graph();
        graph2.addNode("n1");
        graph2.addNode("n2");
        graph3 = new Graph();
        graph3.addNode("n1");
        graph3.addNode("n2");
        graph3.addNode("n3");
        graph3.addChild("n1", "n2", "e1");
        graph3.addChild("n1", "n2", "e2");
        graph3.addChild("n1", "n3", "e1");
        assertTrue(graph2.hasNode("n1") && graph2.hasNode("n2"));
        assertTrue(graph3.getLabels("n1", "n2").contains("e1"));
    }

    @Test
    public void testEmptyGraph() {
        assertEquals(0, graph1.getNodes().size());
    }

    @Test
    public void testAddOne() {
        graph1.addNode("n1");
        assertTrue(graph1.getNodes().contains("n1"));
    }

    @Test
    public void testAddOneMore() {
        graph1.addNode("n2");
        assertTrue(graph1.getNodes().contains("n2"));
    }

    @Test
    public void testAddEdge() {
        graph2.addChild("n1", "n2", "e1");
        assertTrue(graph2.getEdges("n1").containsValue("e1"));
    }

    @Test
    public void testAddDuplicate() {
        graph3.addNode("n1");
        Map<String, Set<String>> m = graph3.getEdges("n1");
        assertTrue(m.containsKey("n2"));
    }

    @Test
    public void testAddDuplicateLabel() {
        graph3.addChild("n1", "n2", "e1");
        Set<String> s = graph3.getLabels("n1", "n2");
        assertTrue(s.contains("e1"));
    }

    @Test
    public void testAddMoreEdge() {
        graph2.addChild("n1", "n2", "e2");
        Set<String> s = graph2.getLabels("n1", "n2");
        assertTrue(s.contains("e2"));
    }

    @Test
    public void testIsChild() {
        assertTrue(graph3.isChildOf("n1", "n2"));
    }

    @Test
    public void testNodeSorted() {
        Set<String> s = graph3.getNodes();
        Iterator<String> it = s.iterator();
        String a = it.next();
        while (it.hasNext()) {
            String b = it.next();
            assertTrue(b.compareTo(a) >= 0);
            a = b;
        }
    }

    @Test
    public void testEdgeSorted() {
        Map<String, Set<String>> m = graph3.getEdges("n1");
        Set<String> s = m.keySet();
        Iterator<String> it = s.iterator();
        String a = it.next();
        while(it.hasNext()) {
            String b = it.next();
            assertTrue(b.compareTo(a) >= 0);
            a = b;
        }
        Set<String> s1 = m.get("n2");
        it = s1.iterator();
        a = it.next();
        String b = it.next();
        assertTrue(b.compareTo(a) >= 0);
    }
}
