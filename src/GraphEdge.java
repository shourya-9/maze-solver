
public class GraphEdge {

    // Instance variables
    private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    // Constructor
    public GraphEdge(GraphNode u, GraphNode v, int type, String label){
        this.u=u;
        this.v=v;
        this.type=type;
        this.label=label;
    }

    // Method to return first endpoint
    public GraphNode firstEndpoint() {
        return u;
    }

    // Method to return second endpoint
    public GraphNode secondEndpoint() {
        return v;
    }

    // Method to return type of edge/ number of coins needed open the door
    public int getType() {
        return type;
    }

    // Method to set edge type
    public void setType(int type) {
        this.type=type;
    }

    // Method to return label of edge
    public String getLabel() {
        return label;
    }

    // Method to set edge label
    public void setLabel(String label) {
        this.label=label;
    }

}
