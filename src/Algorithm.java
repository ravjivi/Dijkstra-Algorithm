import java.awt.Color;
import java.util.ArrayList;
public class Algorithm {
    private ArrayList<Node> nodeList;
    private Node startNode;
    private Node endNode;
    private int algorithmSpeed = 1000;

    public Algorithm (Graph g) {
        nodeList = g.getNodesList(); // Get nodes list
    }

    /**
    * Ensures all links in the graph are two-way.
    * Adds a reverse link if one doesn't already exist.
    */
    public void twoWayLinks() {
        for (int n=0; n<nodeList.size(); n++) { // For every node 
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) { // For every link in that node     
                Node thisNode = nodeList.get(n);
                Node toNode = thisNode.getLinkTo(i);
                if (!checkNodeLink(toNode, thisNode)) { // If no node exists from toNode to thisNode
                    toNode.createLink(thisNode, thisNode.getWeight(i)); // Create this new node
                }            
            }
        }
    }
    /**
     * Helper method to check if a link already exists from one node to another. Returns true if found, otherwise false.
     */
    private boolean checkNodeLink(Node toNode, Node fromNode) {
        for (int k=0; k<toNode.getLinkSize(); k++) { // For every link in toNode
            if (toNode.getLinkTo(k) == fromNode) { 
                return true; // There is a link from toNode to fromNode, so don't need to make one
            }
        }
        return false; // There is no link
    }

    /**
     * Prepares the graph for Dijkstra's algorithm:
     * Sets all node costs to infinity.
     * Sets the start node cost to 0 and identifies start and end nodes.
     * Prints the graph and begins running the algorithm.
     */
    public void setupAlgorithm(Graph g) {
        for (int n=0; n<nodeList.size(); n++) { // For every node
            nodeList.get(n).setCost(Integer.MAX_VALUE); // Cost to that node is infinity
            if (nodeList.get(n).getColor() == Color.GREEN) { // If node is start node
                nodeList.get(n).setCost(0); // Cost to start node is 0
                nodeList.get(n).setPreviousNode(null);
                startNode = nodeList.get(n);
            } else if (nodeList.get(n).getColor() == Color.RED) { // If node is end node
                endNode = nodeList.get(n);
            }
            nodeList.get(n).setVisited(false); // Set unvisited
        }
        twoWayLinks();
        printGraph();
        runDijkstra(g);
    }

    /**
     * Executes Dijkstra’s algorithm:
     * Uses a queue to process nodes.
     * Updates costs and previous nodes if a shorter path is found.
     * Animates the path traversal with color changes.
     * Highlights final shortest path and displays summary.
     */
    public void runDijkstra(Graph g) {
        g.toggleGraphLock(); // Lock graph
        // Create new queue and start with start node
        Queue queue = new Queue();
        queue.enqueue(startNode);
        int linksVisited = 0; // Links visited initially is 0

        while (!queue.isEmpty()) { // While there are still nodes in the queue
            Node currentNode = queue.dequeue(); // Take the next node in the queue
            if (!currentNode.getVisted()) { // If it is not visited
                for (int i=0; i<currentNode.getLinkSize(); i++) {
                    Node nextNode = currentNode.getLinkTo(i); // Node that link from current node is connected to
                    int thisCost = currentNode.getCost() + currentNode.getWeight(i); // Cost to get the next node from current ndoe
                    if (thisCost < nextNode.getCost()) { // If the new path is shorter than the current path to get to next node
                        // Update next node with new path
                        nextNode.setCost(thisCost);
                        nextNode.setPreviousNode(currentNode);
                        queue.enqueue(nextNode); // Queue this next node
                    }
                    
                    // Set animation colour initial
                    currentNode.setLinkColor(i, Color.ORANGE); 
                    // Set opposite direction link to same colour, incase this is drawn on top of the other link
                    for (int k=0; k<nextNode.getLinkSize(); k++) { 
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.ORANGE);
                        }
                    }
                    currentNode.setColor(Color.YELLOW);
                    g.repaint(); // Refresh canvas

                    /* Sleep Screen */
                    try {
                        Thread.sleep(algorithmSpeed); // Sleep for algorithm speed (milliseconds)
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /* Sleep Screen */

                    // Reset animation colour after 
                    currentNode.setLinkColor(i, Color.LIGHT_GRAY);
                    currentNode.setColor(Color.GRAY);
                    for (int k=0; k<nextNode.getLinkSize(); k++) {
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.LIGHT_GRAY);
                        }
                    }
                    linksVisited++;
                }
                currentNode.setVisited(true); // Set this node to visited
            }
        }
        g.toggleGraphLock(); // Unlock graph
        System.out.println("Djikstra done");
        highlightShortestPath();
        g.repaint();
        
        AlgorithmSummary as = new AlgorithmSummary();
        as.showSummary(endNode.getCost(), linksVisited, nodeList); // Open summary dialog (cost to end node, links visited, node array list)
        while (as.showingSummary()) { // While dialog is still open
            highlightShortestPath();
        } 
        resetPath(); // Reset colours
        g.repaint();
    }

    /**
     * Prints all nodes and their connected links to the console.
     * Used for early debugging must nice to keep
     */
    public void printGraph() {
        for (int n=0; n<nodeList.size(); n++) {
            System.out.println("Name: "+nodeList.get(n).getName());
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                System.out.println("    "+"From: "+nodeList.get(n).getName()+", To: "+nodeList.get(n).getLinkTo(i).getName()+", Weight: "+nodeList.get(n).getWeight(i));
            }
        }
    }

    /**
     * Sets the speed of the algorithm animation.
     * A higher speed value results in shorter sleep time.
     */
    public void setAlgorithmSpeed(double speed) {
        algorithmSpeed = (int)(1000/speed); // Divide by speed mulitplier and round to integer
    }

    /**
     * Highlights the shortest path found by Dijkstra’s algorithm in magenta.
     * Traverses from the end node back to the start using previousNode backtracking.
     */
    private void highlightShortestPath() {
        Node currentNode = endNode; // Start from end node
        Node previousNode = endNode.getPreviousNode();
        while (currentNode != startNode) { // While not back to start node
            currentNode.setColor(Color.MAGENTA); // Set current node to magenta
            if (currentNode.getPreviousNode() != null) {
                for (int i=0; i<previousNode.getLinkSize(); i++) {
                    if (previousNode.getLinkTo(i) == currentNode) {
                        previousNode.setLinkColor(i, Color.MAGENTA); // Set link to current node to magenta
                        for (int k=0; k<currentNode.getLinkSize(); k++) {
                            if (currentNode.getLinkTo(k) == previousNode) {
                                currentNode.setLinkColor(k, Color.MAGENTA); // Set link to previous node to magenta
                            }
                        }
                    }
                }
            } else {
                break;
            }
            currentNode = previousNode;
            previousNode = previousNode.getPreviousNode();
            
        }
        currentNode.setColor(Color.MAGENTA); // Set the finak start node to magenta
    }

    /**
     * Resets the colors of all nodes and links after the algorithm completes.
     */
    private void resetPath() {
        // Set every node to white and link to black
        for (int n=0; n<nodeList.size(); n++) {
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                nodeList.get(n).setLinkColor(i, Color.BLACK);
            }
            if (nodeList.get(n) == startNode) {
                nodeList.get(n).setColor(Color.GREEN); // Start node set to green
            } else if (nodeList.get(n) == endNode) {
                nodeList.get(n).setColor(Color.RED); // End node set to red
            } else {
                nodeList.get(n).setColor(Color.WHITE);
            }
        }
    }
}
