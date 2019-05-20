package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * A class for a graph to search for a path with the shortest distance
 */
public class SearchPath {


    /**
     * find the shortest path by distance from {@code start} to {@code end} in {@code graph}
     *
     * @param start the start of the search
     * @param end the end of the search
     * @param graph the graph we are searching the path in
     * @param <Node> the type for Path node
     * @spec.requires {@code start} and {@code end} are nodes of {@code graph}
     * @return a new shortest distance path from the {@code start} to the {@code end} in {@code graph}, {@literal null} if none exists
     */
    public static <Node> Path<Node> findShortestPath(Node start, Node end, Graph<Node, Double> graph) {
        Queue<Path<Node>> active = new PriorityQueue<>(new PathSorter<>());
        Set<Node> finished = new HashSet<>();
        active.add(new Path<>(start));

        // {inv: (active = [] && no path found) || active.remove() is the shortest path}
        while (!active.isEmpty()) {
            Path<Node> minPath = active.remove();
            Node minDest = minPath.getEnd();

            if (minDest.equals(end)) {
                return minPath;
            } else if (!finished.contains(minDest)) {

                // {inv: n from 0 to (i-1), active = active_pre + (minPath + graph.getEdge(minDest)_n)}
                for (Edge<Node, Double> edge: graph.getEdges(minDest)) {
                    Node p = edge.getChild();
                    if (!finished.contains(p)) {
                        Path<Node> newPath = minPath.extend(p, edge.getLabel());
                        active.add(newPath);
                    }
                    finished.add(minDest);
                }
            }
        }
        return null;
    }

    /**
     * this class is for Path to be compatible with PriorityQueue
     */
    private static class PathSorter<Node> implements Comparator<Path<Node>> {
        @Override
        public int compare(Path<Node> p1, Path<Node> p2) {
            return Double.compare(p1.getCost(), p2.getCost());
        }

    }

}
