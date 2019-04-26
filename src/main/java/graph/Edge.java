package graph;

/**
 * Edge represents immutable information of a node, it includes an adjacent node, with a label on its path
 *
 * @spec.specfield to : String // The adjacent node travelling to
 * @spec.specfield label : String // Any necessary information about the path of the 2 nodes
 */

public final class Edge {
    private final String to;
    private final String label;

    /**
     * Creates a new Edge with an adjacent node and a label
     * @param to
     * @param label
     */
    public Edge(String to, String label) {
        throw new RuntimeException("Edge constructor not yet implemented");
    }

    /**
     * get the name of the adjacent node
     * @return the name of the adjacent node
     */
    public String whereTo() {
        throw new RuntimeException("whereTo not yet implemented");
    }

    /**
     * get the label of the path
     * @return the label of the path
     */
    public String getLabel() {
        throw new RuntimeException("getLabel not yet implemented");
    }


}
