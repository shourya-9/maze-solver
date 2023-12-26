
// Importing necessary libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Maze {

    // Instance variables
    private Graph maze;
    private int width;
    private int length;
    private int coins;
    private GraphNode start;
    private GraphNode end;

//    private String[][] test;
//    private int k = 0;
//    private int l = 0;
//    private String[][] test2;

    // Helper method to insert corridor/doorways to rooms
    private void insertEdges(GraphNode u, int i, int j,int index,String line,List<String> lines) throws GraphException {
        // Inserting edge below the node
        if (i + 1 < lines.size()) {
            char nextLineChar = lines.get(i + 1).charAt(j);
            GraphNode v = maze.getNode(index + width);
            // Checking if the next character is a corridor or a door
            if (nextLineChar == 'c') {
                maze.insertEdge(u, v, 0, "corridor");
//                test[k+1][l]="|";
//                test2[k+1][l]=maze.getEdge(u,v).getLabel();
            } else if (Character.isDigit(nextLineChar)) {
                maze.insertEdge(u, v, Character.getNumericValue(nextLineChar), "door");
//                test[k+1][l]=Integer.toString(Character.getNumericValue(nextLineChar));
//                test2[k+1][l]=maze.getEdge(u,v).getLabel();
            }

        }
        // Inserting edge to the right of the node
        if (j + 1 < line.length()) {
            char nextChar = line.charAt(j + 1);
            GraphNode v = maze.getNode(index + 1);
            // Checking if the next character is a corridor or a door
            if (nextChar == 'c') {
                maze.insertEdge(u, v, 0, "corridor");
//                test[k][l+1]="-";
//                test2[k][l+1]=maze.getEdge(u,v).getLabel();
            } else if (Character.isDigit(nextChar)) {
                maze.insertEdge(u, v, Character.getNumericValue(nextChar), "door");
//                test[k][l+1]=Integer.toString(Character.getNumericValue(nextChar));
//                test2[k][l+1]=maze.getEdge(u,v).getLabel();
            }
        }
    }

    // Constructor to read input file and create graph
    public Maze(String inputFile) throws MazeException {
        try {
            // Reading input file
            Scanner sc = new Scanner(new File(inputFile));
            // Reading first 4 lines
            int scale = Integer.parseInt(sc.nextLine().trim());
            this.width = Integer.parseInt(sc.nextLine().trim());
            this.length = Integer.parseInt(sc.nextLine().trim());
            this.coins = Integer.parseInt(sc.nextLine().trim());

            // Initializing graph
            maze = new Graph(width * length);

//            test = new String[5][7];
//            test2 = new String[5][7];
//
//            for(int i=0;i<5;i++){
//                for(int j=0;j<7;j++){
//                    test[i][j]="w";
//                    test2[i][j]=" wall ";
//                }
//            }

            // Creating ArrayList to store remaining lines
            List<String> lines = new ArrayList<>();

            // Reading remaining lines and adding to ArrayList
            while(sc.hasNextLine()){
                String line = sc.nextLine().trim();
                lines.add(line);
            }




            // Going through each line
            int index = 0;
            for(int i= 0; i < lines.size(); i++) {
                String line = lines.get(i);
//                l=0;
                // Checking if all lines are of same length
                if(i>0){
                    if(lines.get(i-1).length()!=line.length())
                        throw new MazeException("Lines are not of same length");
                }

                // Going through each character in the line
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    // Finding nodes
                    if (i % 2 == 0 && j % 2 == 0) {
                        // Checking if is the starting point
                        if (c == 's') {
                            start = maze.getNode(index);
//                            test[k][l]="s";
//                            test2[k][l]=maze.getNode(index).getName()+" start";
                            // Inserting edges to the node
                            insertEdges(start, i, j,index,line,lines);
                            index++;
                        }
                        // Checking if is the ending point
                        else if (c == 'x') {
                            end = maze.getNode(index);
//                            test[k][l]="x";
//                            test2[k][l]=maze.getNode(index).getName()+" end";
                            // Inserting edges to the node
                            insertEdges(end, i, j, index,line,lines);
                            index++;
                        }
                        // Checking if is a room
                        else if (c == 'o') {
                            GraphNode room = maze.getNode(index);
//                            test[k][l]="o";
//                            test2[k][l]=maze.getNode(index).getName()+" room";
                            // Inserting edges to the node
                            insertEdges(room, i, j, index,line,lines);
                            index++;
                        }
//                        l+=2;
                    }

                }
//                k++;
            }

//            for(int i=0;i<5;i++){
//                for(int j=0;j<7;j++){
////                    System.out.print(test[i][j]);
//                }
//                System.out.println();
//            }
//
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 7; j++)
////                    System.out.print(test2[i][j]);
//                System.out.println();
//            }
        }
        // Catching exceptions
        catch (FileNotFoundException | GraphException e) {
            throw new MazeException(e.getMessage());
        }
    }

    // Method to return graph
    public Graph getGraph() throws MazeException {
        if (maze == null)
            throw new MazeException("Graph is null");
        return maze;
    }

    // Method to solve the maze using DFS
    public Iterator<GraphNode> solve() throws GraphException, MazeException {
        // Creating stack to store path
        Stack<GraphNode> path = new Stack<>();
        // Calling DFS method
        boolean check = depthFirstSearch(start, end, coins, path);
        if(check && path!=null) {
            Collections.reverse(path); // Reversing the path
            return path.iterator();
        }
        else
            return null;
    }

    // Helper method to perform DFS
    private boolean depthFirstSearch(GraphNode curr, GraphNode end, int coins, Stack<GraphNode> path) throws MazeException, GraphException {
        // Checking if the current node is the end node
        if(curr == end) {
            path.push(curr); // Pushing the end node to the stack
            return true;
        }

        // Marking the current node
        curr.mark(true);

        // Getting the edges incident on the current node
        Iterator<GraphEdge> iterator = maze.incidentEdges(curr);

        // Going through each edge
        while(iterator.hasNext()) {
            GraphEdge edge = iterator.next();
            GraphNode next = edge.secondEndpoint();
            // Checking if the next node is not marked and the edge type is less than or equal to the remaining coins
            if (!next.isMarked() && edge.getType() <= coins) {
//                path.push(curr);

                // Calling DFS method recursively
                boolean found = depthFirstSearch(next, end, coins - edge.getType(), path);

                // Checking if the path is found
                if (found) {
                    path.push(curr);
                    return true;
                }
//                path.pop();
            }
        }

        // Unmarking the current node
        curr.mark(false);

//        if(path.isEmpty())
//            path.push(end);

        // Returning false if path is not found
        return false;
    }
}
