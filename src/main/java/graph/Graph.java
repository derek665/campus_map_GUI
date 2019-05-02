package graph;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;

/**
 * Graph represent a mutable directed labeled graph
 *
 * Graph is a collection of all the nodes mapped to its edges with labels to its neighbors
 * All edge labels from the same parent and child cannot be same
 *
 */

public class Graph {

    /**
     * @spec.effects create an empty new graph
     */
    public Graph() {
        throw new NotImplementedException("constructor not yet implemented");
    }

    /**
     * return true if there is a node with same name as 'name'
     * @param name the String name of the node
     * @spec.requires name != null
     * @return true iff this has a node in graph with the same name
     */
    public boolean hasNode(String name) {
        throw new NotImplementedException("hasNode not yet implemented");
    }

    /**
     * add a new node to the graph and return true; false if 'name' already exists
     * @param name a string represents the node to be added
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects new element of this, no change if 'name' already exists
     * @return true iff 'name' has not previously been added
     */
    public boolean addNode(String name) {
        throw new NotImplementedException("addNode not yet implemented");
    }

    /**
     * add an edge going from the parent node and return true,
     * no change if 'label' already exists between 'parent' and 'child' and return false
     *
     * @param label a string represent the label of the edge
     * @param child a string represent the child node
     * @param parent a string represent the parent node
     * @throws IllegalArgumentException if graph does not have node 'parent' and 'child'
     * @spec.requires parent != null ; child != null ; label != null
     * @spec.modifies this
     * @spec.effects this.get(parent) = Edge(child, label) + this.get(parent)
     *               no change if same label already exists
     * @return true iff the same edge has not previously been added
     */
    public boolean addChild(String parent, String child, String label) {
        throw new NotImplementedException("addChild not yet implemented");
    }

    /**
     * remove a node from the graph and remove all edges that has 'name' as child
     * @param name the name of the node being removed
     * @spec.requires name != null
     * @spec.modifies this
     * @spec.effects this will not have a node with 'name', and all other nodes will remove their outgoing edges with 'name' as child
     * @throws IllegalArgumentException if graph does not have node 'name'
     */
    public void removeNode(String name) {
        throw new NotImplementedException("addChild not yet implemented");
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
    public void removeEdgeFrom(String parent, String child, String label) {
        throw new NotImplementedException("removeEdgesFrom not yet implemented");
    }

    /**
     * get all the edges going from the parent node
     * @param parent a string represent the parent node
     * @spec.requires parent != null
     * @throws IllegalArgumentException if this does not have key 'parent'
     * @return a map with child node as key in alphabetical order, and mapped to all the labels in alphabetical order
     */
    public Map<String, Set<String>> getEdges(String parent) {
        throw new NotImplementedException("getEdges not yet implemented");
    }

    /**
     * return all the labels the two nodes have in alphabetical order
     * @param parent the start node of the edge
     * @param child the end node of the edge
     * @spec.requires parent != null ; child != null
     * @throws IllegalArgumentException if parent or child is not a key of this
     * @return a set of all the labels between the two nodes in alphabetical order,
     *          empty set if there are no edges from 'parent' to 'child'
     */
    public Set<String> getLabels(String parent, String child) {
        throw new NotImplementedException("getLables not yet implemented");
    }

    /**
     * get all the nodes of the graph in alphabetical order
     * @return a set of all nodes in alphabetical order
     */
    public Set<String> getNodes() {
        throw new NotImplementedException("getEdges not yet implemented");
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
        throw new NotImplementedException("isChildOf not yet implemented");
    }
}


