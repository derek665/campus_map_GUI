package graph;

/**
 * A class representing the edge, with child node and label
 */
public class Edge implements Comparable<Edge> {
    private String child;
    private String label;

    // Representation Invariant for every Edge e is child != null and label != null
    //
    // Abstraction Function: Each edge consist of a child node and a label that is linked to the parent node

    /**
     * @spec.effects create a new edge
     * @param child the child node of the edge
     * @param label the label between the nodes
     */
    public Edge(String child, String label) {
        this.child = child;
        this.label = label;
        checkRep();
    }

    /**
     * return the child node
     * @return the child node of this
     */
    public String getChild() {
        checkRep();
        return child;
    }

    /**
     * return the label of this
     * @return the label of this
     */
    public String getLabel() {
        checkRep();
        return label;
    }


    /**
     * A string representation of this edge
     * @return A string representation in the form of "child(label)"
     */
    @Override
    public String toString() {
        return child + "(" + label + ")";
    }

    /**
     * Standard comparable operations
     *
     * @param other the other edge that is being compared to this
     * @return 1 if this is greater than other, -1 if this is smaller than other, 0 if equal
     */
    @Override
    public int compareTo(Edge other) {
        if (this.child.compareTo(other.child) > 0) {
            return 1;
        } else if (this.child.compareTo(other.child) < 0) {
            return -1;
        } else {
            return this.label.compareTo(other.label);
        }
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

    /**
     * throws exception if rep invariant is violated
     */
    private void checkRep() {
        assert child != null : "child cannot be null";
        assert label != null : "label cannot be null";
    }
}

