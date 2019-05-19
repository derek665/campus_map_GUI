package graph;

import java.util.*;

/**
 * Graph represent a mutable directed labeled graph
 *
 * Graph is a collection of all the nodes mapped to its edges with labels to its neighbors
 * All edge labels from the same parent and child cannot be same
 *
 * @param <Node> the type of the node
 * @param <Label> the type of the edge label
 */

public class Graph<Node, Label> {
    private static final boolean RUN_CHECK_REP = false; // indicate whether checkRep() is enabled

    /**
     * A graph holding all the nodes
     */
    private final Map<Node, Set<Edge<Node, Label>>> graph;

    // Representation Invariant for every Graph g:
    // g != null, all nodes, edges and labels of g != null
    // Every child node of an edge must be a node (key) of g
    //
    // Abstraction Function:
    // Each node in the graph is mapped to a collection of its outgoing labelled edges,
    // each edge consists of a child node and a label
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
     *
     * @param name the name of the node
     * @return true iff this has a node in graph with the same name
     * @spec.requires name != null
     */
    public boolean hasNode(Node name) {
        checkRep();
        boolean a = graph.containsKey(name);
        checkRep();
        return a;
    }

    /**
     * return true if there is an edge with same label between parent and child
     *
     * @param parent the parent node of the edge
     * @param child  the child node of the edge
     * @param label  the label of the edge between parent and child
     * @return true iff parent and child has an edge with the label
     * @spec.requires parent and child are a node of the graph
     */
    public boolean hasLabel(Node parent, Node child, Label label) {
        checkRep();
        boolean a = graph.get(parent).contains(new Edge<>(child, label));
        checkRep();
        return a;
    }

    /**
     * return true if there is the outgoing edge from parent
     *
     * @param parent the starting point of the edge
     * @param edge the edge that is being checked
     * @return true iff parent has the outgoing edge
     * @spec.requires parent is a node of this
     */
    public boolean hasEdge(Node parent, Edge<Node, Label> edge) {
        checkRep();
        return hasLabel(parent, edge.getChild(), edge.getLabel());
    }

    /**
     * add a new node to the graph and return whether the node is successfully added to graph
     *
     * @param name the new node to be added
     * @return true iff 'name' has not previously been added
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects new element of this, no change if 'name' already exists
     */
    public boolean addNode(Node name) {
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
     *
     * @param label  the label of the edge
     * @param child  the child node
     * @param parent the parent node
     * @return true iff the same edge has not previously been added
     * @throws IllegalArgumentException if graph does not have node 'parent' and 'child'
     * @spec.requires parent != null ; child != null ; label != null
     * @spec.modifies this
     * @spec.effects a new outgoing edge for 'parent' with a child node and a label
     * no change if same label already exists
     */
    public boolean addChild(Node parent, Node child, Label label) {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("parent/child node does not exist");
        }
        Set<Edge<Node, Label>> edges = graph.get(parent);
        Edge<Node, Label> newEdge = new Edge<>(child, label);
        boolean added = false;
        if (!edges.contains(newEdge)) {
            added = true;
            edges.add(newEdge);
        }
        checkRep();
        return added;
    }

    /**
     * add an edge going from the parent node and return whether the edge is successfully added
     *
     * @param parent the parent node
     * @param edge the edge of has parent as parent node
     * @return true iff the same edge has not previously been added
     * @throws IllegalArgumentException if graph does not have parent node, or child node does not exists
     * @spec.requires parent != null ; edge != null
     * @spec.modifies this
     * @spec.effects a new outgoing edge for parent
     */
    public boolean addChild(Node parent, Edge<Node, Label> edge) {
        checkRep();
        return addChild(parent, edge.getChild(), edge.getLabel());
    }

    /**
     * remove a node from the graph and remove all edges that has 'name' as child
     *
     * @param name the name of the node being removed
     * @throws IllegalArgumentException if graph does not have node 'name'
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects this will not have a node with 'name', and all other nodes will remove their outgoing edges with 'name' as child
     */
    public void removeNode(Node name) {
        checkRep();
        if (!graph.containsKey(name)) {
            throw new IllegalArgumentException("node does not exist");
        }
        for (Node node : graph.keySet()) {
            if (isChildOf(name, node)) {
                Set<Edge<Node, Label>> s = graph.get(node);
                Iterator<Edge<Node, Label>> it = s.iterator();
                while (it.hasNext()) {
                    Edge e = it.next();
                    if (e.getChild().equals(name)) {
                        it.remove();
                    }
                }
            }
        }
        graph.remove(name);
        checkRep();
    }

    /**
     * remove an edge with 'label' that connects from 'parent' to 'child'
     *
     * @param parent the starting point of the edge
     * @param child  the end point of the edge
     * @param label  the label of the edge that is being removed
     * @throws IllegalArgumentException if graph does not have node 'parent' or 'child', or does not have an edge between with 'label'
     * @spec.requires parent != null ; child != null ; label != null
     * @spec.modifies this
     * @spec.effects this.getLabels(' parent ', ' child ') = this.getLabels('parent', 'child') - label
     */
    public void removeEdgeFrom(Node parent, Node child, Label label) {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("node does not exist");
        }
        Edge edge = new Edge<>(child, label);
        boolean removed = graph.get(parent).remove(edge);
        if (!removed) {
            throw new IllegalArgumentException("label does not exist");
        }
        checkRep();
    }

    /**
     * remove an edge with 'label' that connects from 'parent' to 'child'
     *
     * @param parent the starting point of the edge
     * @param edge the outgoing edge from parent
     * @throws IllegalArgumentException if graph does not have node 'parent' or the parent node does not have 'edge'
     * @spec.requires parent != null ; edge != null
     * @spec.modifies this
     * @spec.effects this.hasLabels(parent, edge) = false
     */
    public void removeEdgeFrom(Node parent, Edge<Node, Label> edge) {
        checkRep();
        removeEdgeFrom(parent, edge.getChild(), edge.getLabel());
    }

    /**
     * get all the edges going from the parent node
     *
     * @param parent the parent node of the graph
     * @return a map with child node as key, and mapped to all the labels
     * @throws IllegalArgumentException if this does not have key 'parent'
     * @spec.requires parent != null
     */
    public Set<Edge<Node, Label>> getEdges(Node parent) {
        checkRep();
        if (!graph.containsKey(parent)) {
            throw new IllegalArgumentException("parent node does not exist");
        }
        Set<Edge<Node, Label>> set = new HashSet<>(graph.get(parent));
        checkRep();
        return set;
    }

    /**
     * return all the labels the two nodes have
     *
     * @param parent the start node of the edge
     * @param child  the end node of the edge
     * @return a set of all the labels between the two nodes,
     * empty set if there are no edges from 'parent' to 'child'
     * @throws IllegalArgumentException if parent or child is not a key of this
     * @spec.requires parent != null ; child != null
     */
    public Set<Label> getLabels(Node parent, Node child) {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child)) {
            throw new IllegalArgumentException("parent/child node does not exist");
        }
        Set<Edge<Node, Label>> edges = graph.get(parent);
        Set<Label> result = new HashSet<>();
        for (Edge<Node, Label> e : edges) {
            if (e.getChild().equals(child)) {
                result.add((e.getLabel()));
            }
        }
        checkRep();
        return result;
    }

    /**
     * get all the nodes of the graph
     *
     * @return a set of all nodes in this
     */
    public Set<Node> getNodes() {
        checkRep();
        Set<Node> s = new HashSet<>(graph.keySet());
        checkRep();
        return s;
    }

    /**
     * return if 'child' node has an edge going from the parent to child
     *
     * @param child  the child node being tested
     * @param parent the parent node being tested
     * @return true iff there is an edge from parent to child
     * @throws IllegalArgumentException if this does not have key 'child' or 'parent'
     * @spec.requires child != null ; parent != null
     */
    public boolean isChildOf(Node child, Node parent) {
        checkRep();
        if (!graph.containsKey(child) || !graph.containsKey(parent)) {
            throw new IllegalArgumentException("node does not exist");
        }
        boolean found = false;
        Set<Edge<Node, Label>> edges = graph.get(parent);
        for (Edge e : edges) {
            if (e.getChild().equals(child)) {
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
        assert (graph != null) : "graph cannot be null";

        if (RUN_CHECK_REP) {
            Set<Node> nodes = graph.keySet();
            for (Node s : nodes) {
                assert (nodes != null) : "nodes cannot be null";
                Set<Edge<Node, Label>> edges = graph.get(s);
                for (Edge<Node, Label> e : edges) {
                    assert (e.getChild() != null) : "child node cannot be null";
                    assert (nodes.contains(e.getChild())) : "child node must be a node of graph";
                    assert (e.getLabel() != null) : "edge label cannot be null";
                }
            }
        }
    }
}

