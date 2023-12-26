
public class GraphNode {

    // Instance variables
    private int name;
    private boolean mark;

    // Constructor
    public GraphNode(int name){
        this.name=name;
        this.mark=false;
    }

    // Method to mark the node
    public void mark(boolean mark){
        this.mark=mark;
    }

    // Method to check if the node is marked
    public boolean isMarked() {
        return mark;
    }

    // Method to return node name
    public int getName() {
        return name;
    }
}
