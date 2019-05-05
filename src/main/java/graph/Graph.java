package graph;

import java.util.*;

/**
 * Graph represent a mutable directed labeled graph
 *
 * Graph is a collection of all the nodes mapped to its edges with labels to its neighbors
 * All edge labels from the same parent and child cannot be same
 *
 */

public class Graph {
    private static final boolean RUN_CHECK_REP = false; // indicate whether checkRep() is enabled

    private final Map<String, Set<Edge>> graph;

    // Representation Invariant for every Graph g:
    // g != null, all nodes, edges and labels of g != null
    // Every child node of an edge must be a node (key) of g
    //
    // Abstraction Function:
    // Each node in the graph is mapped to a collection of its outgoing labelled edges
    //

    /**
     * @spec.effects create an empty new graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * return true if there is a node with same name as 'name'
     * @param name the String name of the node
     * @spec.requires name != null
     * @return true iff this has a node in graph with the same name
     */
    public boolean hasNode(String name) {
        checkRep();
        boolean a = graph.containsKey(name);
        checkRep();
        return a;
    }

    /**
     * add a new node to the graph and return whether the node is successfully added to graph
     * @param name a string represents the node to be added
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects new element of this, no change if 'name' already exists
     * @return true iff 'name' has not previously been added
     */
    public boolean addNode(String name) {
        checkRep();
        boolean added = false;
        if (!graph.containsKey(name)) {
            added = true;
            graph.put(name, new HashSet<>());
        }
        checkRep();
        return added;
    }

    /**
     * add an edge going from the parent node and return whether the edge is successfully added
     * @param label a string represent the label of the edge
     * @param child a string represent the child node
     * @param parent a string represent the parent node
     * @throws IllegalArgumentException if graph does not have node 'parent' and 'child'
     * @spec.requires parent != null ; child != null ; label != null
     * @spec.modifies this
     * @spec.effects a new outgoing edge for 'parent' with a child node and a label
     *               no change if same label already exists
     * @return true iff the same edge has not previously been added
     */
    public boolean addChild(String parent, String child, String label) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("parent/child node does not exist");
        }
        Set<Edge> edges = graph.get(parent);
        Edge newEdge = new Edge(child, label);
        boolean added = false;
        if (!edges.contains(newEdge)) {
            added = true;
            edges.add(newEdge);
        }
        checkRep();
        return added;
    }

    /**
     * remove a node from the graph and remove all edges that has 'name' as child
     * @param name the name of the node being removed
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects this will not have a node with 'name', and all other nodes will remove their outgoing edges with 'name' as child
     * @throws IllegalArgumentException if graph does not have node 'name'
     */
    public void removeNode(String name) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(name)) {
            throw new IllegalArgumentException("node does not exist");
        }
        for (String node : graph.keySet()) {
            if (isChildOf(name, node)) {
                Set<Edge> s = graph.get(node);
                Iterator<Edge> it = s.iterator();
                while (it.hasNext()) {
                    Edge e = it.next();
                    if (e.child.equals(name)) {
                        it.remove();
                    }
                }
            }
        }
        graph.remove(name);
        checkRep();
    }

    /**
     * remove an the edge with 'label' that connects from 'parent' to 'child'
     * @param parent the starting point of the edge
     * @param child the end point of the edge
     * @param label the label of the edge that is being removed
     * @spec.requires parent != null ; child != null ; label != null
     * @spec.modifies this
     * @spec.effects this.getLabels('parent', 'child') = this.getLabels('parent', 'child') - label
     * @throws IllegalArgumentException if graph does not have node 'parent' or 'child', or does not have an edge between with 'label'
     */
    public void removeEdgeFrom(String parent, String child, String label) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("node does not exist");
        }
        Edge edge = new Edge(child, label);
        boolean removed = graph.get(parent).remove(edge);
        if (!removed) {
            throw new IllegalArgumentException("label does not exist");
        }
        checkRep();
    }

    /**
     * get all the edges going from the parent node
     * @param parent a string represent the parent node
     * @spec.requires parent != null
     * @throws IllegalArgumentException if this does not have key 'parent'
     * @return a map with child node as key, and mapped to all the labels
     */
    public Map<String, Set<String>> getEdges(String parent) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(parent)) {
            throw new IllegalArgumentException("parent node does not exist");
        }
        Map<String, Set<String>> map = new HashMap<>();
        for (Edge e : graph.get(parent)) {
            if (!map.containsKey(e.child)) {
                map.put(e.child, new HashSet<>());
            }
            map.get(e.child).add(e.label);
        }
        checkRep();
        return map;
    }

    /**
     * return all the labels the two nodes have
     * @param parent the start node of the edge
     * @param child the end node of the edge
     * @spec.requires parent != null ; child != null
     * @throws IllegalArgumentException if parent or child is not a key of this
     * @return a set of all the labels between the two nodes,
     *          empty set if there are no edges from 'parent' to 'child'
     */
    public Set<String> getLabels(String parent, String child) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("parent/child node does not exist");
        }
        Set<Edge> edges = graph.get(parent);
        Set<String> result = new HashSet<>();
        for (Edge e : edges) {
            if (e.child.equals(child)) {
                result.add(e.label);
            }
        }
        checkRep();
        return result;
    }

    /**
     * get all the nodes of the graph
     * @return a set of all nodes in this
     */
    public Set<String> getNodes() {
        checkRep();
        Set<String> s = new HashSet<>();
        s.addAll(graph.keySet());
        checkRep();
        return s;
    }

    /**
     * return if 'child' node has an edge going from the parent to child
     * @param child a string represent the child node
     * @param parent a string represent the parent node
     * @throws IllegalArgumentException if this does not have key 'child' or 'parent'
     * @spec.requires child != null ; parent != null
     * @return true iff there is an edge from parent to child
     */
    public boolean isChildOf(String child, String parent) throws IllegalArgumentException {
        checkRep();
        if (!graph.containsKey(child) || !graph.containsKey(parent)) {
            throw new IllegalArgumentException("node does not exist");
        }
        boolean found = false;
        Set<Edge> edges = graph.get(parent);
        for (Edge e : edges) {
            if (e.child.equals(child)) {
                found = true;
            }
        }
        checkRep();
        return found;
    }

    /**
     * throws exception if representation invariant is violate
     */
    private void checkRep() {
        if (RUN_CHECK_REP) {
            assert (graph != null) : "graph cannot be null";

            Set<String> nodes = graph.keySet();
            for (String s : nodes) {
                assert (nodes != null) : "nodes cannot be null";
                Set<Edge> edges = graph.get(s);
                for (Edge e : edges) {
                    assert (e.child != null) : "child node cannot be null";
                    assert (nodes.contains(e.child)) : "child node must be a node of graph";
                    assert (e.label != null) : "edge label cannot be null";
                }
            }
        }
    }

    /**
     * A class representing the edge, with child node and label
     */
    private class Edge {
        private String child;
        private String label;

        /**
         * @spec.effects create a new edge
         * @param child the child node of the edge
         * @param label the label between the nodes
         */
        private Edge(String child, String label) {
            this.child = child;
            this.label = label;
        }

        /**
         * Standard equality operation.
         *
         * @param o the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Edge and 'this' and 'obj' represent
         *     the same child and same label
         */
        @Override
        public boolean equals(Object o) {
            if (! (o instanceof Edge)) {
                return false;
            } else {
                Edge e = (Edge) o;
                return this.child.equals(e.child) && this.label.equals(e.label);
            }
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            return child.hashCode() + label.hashCode();
        }
    }
}


