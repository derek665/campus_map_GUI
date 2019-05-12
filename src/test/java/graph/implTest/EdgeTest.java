package graph.implTest;

import static org.junit.Assert.*;
import java.util.*;
import graph.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * This class contains a set of test cases that can be used to test the implementation of Edge class
 */

public class EdgeTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Edge edge1;

    @Before
    public void setUp() {
        edge1 = new Edge("n2", "e1");
    }

    @Test
    public void testGetChild() {
        assertEquals("n2", edge1.getChild());
    }

    @Test
    public void testGetLabel() {
        assertEquals("e1", edge1.getLabel());
    }
}
