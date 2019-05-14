package marvel;

import graph.*;

import java.util.*;

/** find the shortest path between 2 characters */
public class MarvelPaths {


    /**
     * get inputs from user and output the result
     * @param args command line
     */
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
//        Map<String, Set<String>> bookData =
//                MarvelParser.parseData("src/test/resources/marvel/data/marvel.tsv");
        Graph graph = MarvelParser.parseData("src/test/resources/marvel/data/marvel.tsv");
        System.out.println("Search path between 2 characters");
        System.out.println("from: ");
        String start = input.nextLine();
        while (!graph.hasNode(start)) {
            System.out.println(start + " is not a character of marvel");
            System.out.println("from: ");
            start = input.nextLine();
        }
        System.out.println("to: ");
        String end = input.nextLine();
        while (!graph.hasNode(end)) {
            System.out.println(end + " is not a character of marvel");
            System.out.println("to: ");
            end = input.nextLine();
        }
        List<Edge> result = findPath(start, end, graph);
        System.out.println();
        if (result.isEmpty() && !start.equals(end)) {
            System.out.println(start + " has no connection to " + end + " at all");
        } else {
            System.out.println("the connections from " + start + " to " + end + " are:");
            for (Edge e : result) {
                System.out.println(start + " is in " + e.getLabel() + " with " + e.getChild());
                start = e.getChild();
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
     * @return the path between the 2 characters
     */
    public static List<Edge> findPath(String start, String end, Graph graph) {
        Queue<String> q = new LinkedList<>(); // nodes to visit
        Map<String, List<Edge>> m = new HashMap<>(); // each key in m is a visited node, mapped to a path
        q.add(start);
        m.put(start, new ArrayList<>());
        while (!q.isEmpty()) {
            String node = q.remove();
            if (node.equals(end)) {
                return new ArrayList<>(m.get(node));
            } else {
                Set<Edge> edges = new TreeSet<>(graph.getEdges(node));
                for (Edge edge : edges) {
                    if (!m.containsKey(edge.getChild())) {
                        List<Edge> p = m.get(node);
                        List<Edge> p2 = new ArrayList<>();
                        if (p != null) {
                            p2.addAll(p);
                        }
                        p2.add(edge);
                        m.put(edge.getChild(), p2);
                        q.add(edge.getChild());
                    }
                }
            }
        }
        return new ArrayList<>();
    }
}