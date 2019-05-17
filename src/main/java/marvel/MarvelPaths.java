package marvel;

import graph.*;

import java.util.*;

/** find the shortest path between 2 characters */
public class MarvelPaths {


    /**
     * get inputs from user and output the result
     * @param args command line
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Graph graph = MarvelParser.parseData("src/test/resources/marvel/data/marvel.tsv");
        System.out.println("Search path between 2 characters");
        System.out.println("from: ");
        String start = input.nextLine();
        System.out.println("to: ");
        String end = input.nextLine();
        if (graph.hasNode(start) && graph.hasNode(end)) {
            List<Edge> result = findPath(start, end, graph);
            System.out.println();
            if (result.isEmpty() && !start.equals(end)) {
                System.out.println(start + " has no connection to " + end + " at all");
            } else {
                System.out.println("the connections from " + start + " to " + end + " are:");
                // {inv: start = result.get(i).getChild()}
                for (Edge e : result) {
                    System.out.println(start + " is in " + e.getLabel() + " with " + e.getChild());
                    start = e.getChild();
                }
            }
        } else {
            if (!graph.hasNode(start)) {
                System.out.println("unknown character " + start);
            }
            if (!graph.hasNode(end)) {
                System.out.println("unknown character " + end);
            }
        }
    }

    /**
     * Compute the shortest path between the 2 characters
     *
     * @param start the start of the character search
     * @param end the end of the character search
     * @param graph the graph that we are searching in
     * @spec.requires start != null ; end != null ; graph != null
     * @return the list of paths between the 2 characters
     */
    public static List<Edge> findPath(String start, String end, Graph graph) {
        Queue<String> queue = new LinkedList<>(); // nodes to visit
        Map<String, List<Edge>> visited = new HashMap<>(); // each key in visited is a visited node, mapped to a path
        queue.add(start);
        visited.put(start, new ArrayList<>());
        // {inv : (q = [] && no path found || q = [n_1, n_2, ... n_n]) && visited.get(n_i) = i nodes away from start}
        while (!queue.isEmpty()) {
            String node = queue.remove();
            if (node.equals(end)) {
                return new ArrayList<>(visited.get(node));
            } else {
                Set<Edge> edges = new TreeSet<>(graph.getEdges(node));
                // {inv: queue = queue_pre + edges_(i-1) && visited.get(node) = visited.get(node)_pre + edges(i-1)}
                for (Edge edge : edges) {
                    if (!visited.containsKey(edge.getChild())) {
                        List<Edge> p = visited.get(node);
                        List<Edge> p2 = new ArrayList<>();
                        if (p != null) {
                            p2.addAll(p);
                        }
                        p2.add(edge);
                        visited.put(edge.getChild(), p2);
                        queue.add(edge.getChild());
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}