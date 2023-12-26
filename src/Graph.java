
// Importing necessary libraries
import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT {

    // Instance variables
    private int numNodes; // Number of nodes in the graph
    private GraphEdge[][] adjMatrix; // Adjacency matrix to represent edges between nodes
    private GraphNode[] nodes; //Array to store nodes

    // Constructor
    public Graph(int n) {
        this.numNodes=n;
        this.adjMatrix=new GraphEdge[n][n];
        this.nodes=new GraphNode[n];
        // Initializing nodes
        for(int i=0; i<n; i++){
            nodes[i]=new GraphNode(i);
        }
    }

    // Helper method to check if node exists
    private boolean nodeExists(GraphNode u) {
        if (u.getName()<0 || u.getName()>=numNodes) {
            return false;
        }
        return true;
    }

    // Method to insert an edge connecting nodes
    public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException{
        if(!nodeExists(u) || !nodeExists(v))
            // Throwing exception if node does not exist
            throw new GraphException("Node does not exist");
        else{
            if(adjMatrix[u.getName()][v.getName()] != null){
                // Throwing exception if edge already exists
                throw new GraphException("Edge already exists");
            }
            else {
                // creating edges
                adjMatrix[u.getName()][v.getName()]=new GraphEdge(u,v,type,label);
                adjMatrix[v.getName()][u.getName()]=new GraphEdge(v,u,type,label);
            }
        }
    }

    // Method to return specific node
    public GraphNode getNode(int name) throws GraphException{
        // Checking if node exists
        if(name<0 || name>=numNodes)
            // Throwing exception if node does not exist
            throw new GraphException("Node does not exist");
        else
            return nodes[name];
    }

    // Method to return an Iterator storing all edges incident on node
    public Iterator incidentEdges(GraphNode u) throws GraphException {
        if (!nodeExists(u))
            // Throwing exception if node does not exist
            throw new GraphException("Node does not exist");
        else {
            Stack stack = new Stack(); // Creating stack to store edges
            for (int i = 0; i < numNodes; i++) {
                if (adjMatrix[u.getName()][i] != null) {
                    stack.push(adjMatrix[u.getName()][i]); // Pushing edges to stack
                }
            }
            return stack.iterator();
        }
    }

    // Method to return specific edge
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        if (!nodeExists(u) || !nodeExists(v))
            // Throwing exception if node does not exist
            throw new GraphException("Node does not exist");
        else {
            if (adjMatrix[u.getName()][v.getName()] == null)
                // Throwing exception if edge does not exist
                throw new GraphException("Edge does not exist");
            else
                return adjMatrix[u.getName()][v.getName()];
        }
    }

    // Method to check if two nodes are adjacent
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        if (!nodeExists(u) || !nodeExists(v))
            // Throwing exception if node does not exist
            throw new GraphException("Node does not exist");
        else {
            if (adjMatrix[u.getName()][v.getName()] == null)
                return false;
            else
                return true;
        }
    }
}




