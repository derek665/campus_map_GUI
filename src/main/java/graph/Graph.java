package graph;

import java.util.*;

/**
 * Graph represent a mutable directed labeled graph,
 * Graph is a collection of all the nodes and its edges to its neighbors
 * All edge labels from the same parent and child cannot be same
 */

public class Graph {

    /**
     * @spec.effects create an empty new graph
     */
    public Graph() {
        throw new RuntimeException("constructor not yet implemented");
    }

    /**
     * return true if there is a node with same name as 'name'
     * @param name the String name of the node
     * @spec.requires name != null
     * @return true iff has a node in graph with the same name
     */
    public boolean hasNode(String name) {
        throw new RuntimeException("hasNode not yet implemented");
    }

    /**
     * add a new node to the graph
     * @param name a string represents the node to be added
     * @spec.requries name != null
     * @spec.modifies this
     * @spec.effects new elemnent of this, no change if n already exists
     */
    public void addNode(String name) {
        throw new RuntimeException("addNode not yet implemented");
    }

    /**
     * add an edge going from the parent node
     * @param label a string represent the label of the edge
     * @param destination a string represent the child node
     * @param parent a string represent the parent node
     * @throws IllegalArgumentException if this does not have key 'parent' or 'destination'
     * @spec.requires parent != null ; destination != null ; label != null
     * @spec.modifies this
     * @spec.effects this.get(parent) = Edge(destination, label) :: this.get(parent)
     *               no change if same label already exists
     */
    public void addChild(String parent, String destination, String label) {
        throw new RuntimeException("addChild not yet implemented");
    }

    /**
     * get all the edges going from the parent node
     * @param parent a string represent the parent node
     * @spec.requires parent != null
     * @throws IllegalArgumentException if this does not have key 'parent'
     * @return a map with child node as key, and mapped to all the labels in alphabetical order
     */
    public Map<String, Set<String>> getEdges(String parent) {
        throw new RuntimeException("getEdges not yet implemented");
    }

    /**
     * return all the labels the two nodes have
     * @param parent the start node of the edge
     * @param child the end node of the edge
     * @spec.requires parent != null ; child != null
     * @throws IllegalArgumentException if parent or child is not a key of this, or no edges are found from parent to child
     * @return a set of all the labels between the two nodes
     */
    public Set<String> getLabels(String parent, String child) {
        throw new RuntimeException("getLables not yet implemented");
    }

    /**
     * get all the nodes of the graph
     * @return a set of all nodes in alphabetical order
     */
    public Set<String> getNodes() {
        throw new RuntimeException("getEdges not yet implemented");
    }

    /**
     * return if 'child' node has an edge going from the parent to child
     * @param child a string represent the child node
     * @param parent a string represent the parent node
     * @throws IllegalArgumentException if this does not have key 'child' or 'parent'
     * @spec.requires child != null ; parent != null
     * @return true iff there is an edge from parent to child
     */
    public boolean isChildOf(String child, String parent) {
        throw new RuntimeException("isChildOf not yet implemented");
    }
}

final class Edge implements Comparable<Edge> {

    /**
     * @param destination the adjacent node
     * @param label the labe of the path
     * @spec.effects construct a new edge with adjacent node and a label
     */
    public Edge(String destination, String label) {
        throw new RuntimeException("Edge constructor not yet implemented");
    }

    /**
     * compare this edge to another edge
     * @param e the other edge this is comparing to
     * @return 1 if this > e ; 0 if this == e ; -1 if this < e
     */
    @Override
    public int compareTo(Edge e) {
        throw new RuntimeException("compareTo not yet implemented");
    }
}


