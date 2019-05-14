package marvel.implTest;

import static org.junit.Assert.*;
import marvel.*;
import graph.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.*;

/**
 * This class contains a set of test cases for the implementations of the MarvelPaths class
 */
public class MarvelPathsTest {
	@Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

	private Graph graph;

	@Before
	public void setUp() {
		graph = MarvelParser.parseData("src/test/resources/marvel/data/soccer.tsv");
	}

	@Test
	public void testNodeSize() {
		assertEquals(10, graph.getNodes().size());
	}

	@Test
	public void testEdgeBuild() {
		Set<Edge> set = graph.getEdges("Arsenal");
		assertTrue(set.contains(new Edge("Liverpool", "EPL")));
		assertTrue(set.contains(new Edge("Southampton", "EPL")));
		assertTrue(set.contains(new Edge("Valencia", "European-League")));
	}

	@Test
	public void testFindPath() {
		List<Edge> result = MarvelPaths.findPath("Barcelona", "Southampton", graph);
		List<Edge> expected = new ArrayList<>();
		expected.add(new Edge("Valencia", "La-Liga"));
		expected.add(new Edge("Arsenal", "European-League"));
		expected.add(new Edge("Southampton", "EPL"));
		assertEquals(expected, result);
	}

	@Test
	public void testNoPath() {
		assertTrue(MarvelPaths.findPath("Hanover96", "Eibar", graph).isEmpty());
	}

	@Test
	public void testReflexivePath() {
		assertTrue(MarvelPaths.findPath("PSG", "PSG", graph).isEmpty());
	}
}
