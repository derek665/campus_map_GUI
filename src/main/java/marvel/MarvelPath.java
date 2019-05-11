package marvel;

import graph.Graph;

import java.util.*;

public class MarvelPath {

    public static void main(String args[]) {
        Map<String, Set<String>> bookData =
                MarvelParser.parseData("/Users/derekchan/cse331-19sp-derek665/src/main/resources/marvel/data/staffSuperheroes.tsv");
        Graph graph = buildGraph(bookData);
        System.out.println(bookData.size());
    }

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
