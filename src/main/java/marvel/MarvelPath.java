package marvel;

import graph.Graph;

import java.util.*;

/** find the shortest path between 2 characters */
public class MarvelPath {


    /**
     * get inputs from user and output the result
     * @param args
     */
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        Map<String, Set<String>> bookData =
                MarvelParser.parseData("/Users/derekchan/cse331-19sp-derek665/src/main/resources/marvel/data/marvel.tsv");
        Graph graph = buildGraph(bookData);
        System.out.println("Search path between 2 characters");
        System.out.print("from: ");
        String start = input.nextLine();
        while (!graph.hasNode(start)) {
            System.out.println(start + " is not a character of marvel");
            System.out.print("from: ");
            start = input.nextLine();
        }
        System.out.print("to: ");
        String end = input.nextLine();
        while (!graph.hasNode(end)) {
            System.out.println(end + " is not a character of marvel");
            System.out.print("to: ");
            end = input.nextLine();
        }
        System.out.println(findPath(start, end, graph));
    }

    /**
     * Compute the shortest path between the 2 characters
     *
     * @param start the start of the character search
     * @param end the end of the character search
     * @param graph the graph that we are searching in
     * @return the path between the 2 characters
     */
    private static List<String> findPath(String start, String end, Graph graph) {
        Queue<String> q = new LinkedList<>();
        Map<String, List<String>> m = new HashMap<>();
        q.add(start);
        m.put(start, new ArrayList<>());
        while (!q.isEmpty()) {
            String node = q.remove();
            if (node.equals(end)) {
                return m.get(node);
            } else {
                Map<String, Set<String>> edges = graph.getEdges(node);
                for (String child : edges.keySet()) {
                    if (!m.containsKey(child)) {
                        List<String> p = m.get(node);
                        List<String> p2 = new ArrayList<>();
                        if (p != null) {
                            p2.addAll(p);
                        }
                        p2.add(child);
                        m.put(child, p2);
                        q.add(child);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * create a graph of all the label and nodes in map
     *
     * @spec.requires map != null
     * @param map the label mapped to all nodes
     * @return A graph with all the data from the file
     */
    private static Graph buildGraph(Map<String, Set<String>> map) {
        Graph graph = new Graph();
        for (String book : map.keySet()) {
            List<String> arr = new ArrayList<>(map.get(book));
            int i = 0;
            while (i < arr.size()) {
                String hero = arr.get(i);
                if (!graph.hasNode(hero)) {
                    graph.addNode(hero);
                }
                int j = i + 1;
                while (j < arr.size()) {
                    String child = arr.get(j);
                    if (!graph.hasNode(child)) {
                        graph.addNode(child);
                    }
                    if (!graph.hasLabel(hero, child, book) && !hero.equals(child)) {
                        graph.addChild(hero, child, book);
                        graph.addChild(child, hero, book);
                    }
                    j++;
                }
                i++;
            }
        }
        return graph;
    }

}
