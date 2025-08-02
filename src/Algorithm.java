import java.awt.Color;
import java.util.ArrayList;
public class Algorithm {
    private ArrayList<Node> nodeList;
    private Node startNode;
    private Node endNode;
    private int algorithmSpeed = 1000;

    public Algorithm (Graph g) {
        nodeList = g.getNodesList();  
    }

    public void twoWayLinks() {
        for (int n=0; n<nodeList.size(); n++) {
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {              
                Node thisNode = nodeList.get(n);
                Node toNode = thisNode.getLinkTo(i);
                if (!checkNodeLink(toNode, thisNode)) {
                    toNode.createLink(thisNode, thisNode.getWeight(i));
                }            
            }
        }
    }
    private boolean checkNodeLink(Node toNode, Node fromNode) {
        for (int k=0; k<toNode.getLinkSize(); k++) {
            if (toNode.getLinkTo(k) == fromNode) {
                return true;
            }
        }
        return false;
    }

    public void setupAlgorithm(Graph g) {
        for (int n=0; n<nodeList.size(); n++) {
            nodeList.get(n).setCost(Integer.MAX_VALUE);
            if (nodeList.get(n).getColor() == Color.GREEN) { // If node is start node
                nodeList.get(n).setCost(0);
                nodeList.get(n).setPreviousNode(null);
                startNode = nodeList.get(n);
            } else if (nodeList.get(n).getColor() == Color.RED) {
                System.out.println("End node set");
                endNode = nodeList.get(n);
            }
            nodeList.get(n).setVisited(false);
        }
        twoWayLinks();
        printGraph();
        runDijkstra(g);
    }

    public void runDijkstra(Graph g) {
        g.toggleGraphLock();
        Queue queue = new Queue();
        queue.enqueue(startNode);
        int linksVisited = 0; // Links visited

        while (!queue.isEmpty()) {
            Node currentNode = queue.dequeue();
            if (!currentNode.getVisted()) {
                for (int i=0; i<currentNode.getLinkSize(); i++) {
                    int thisCost = currentNode.getCost() + currentNode.getWeight(i);
                    Node nextNode = currentNode.getLinkTo(i);
                    if (thisCost < nextNode.getCost()) {
                        nextNode.setCost(thisCost);
                        nextNode.setPreviousNode(currentNode);
                        queue.enqueue(nextNode);
                    }
                    currentNode.setLinkColor(i, Color.GREEN);
                    for (int k=0; k<nextNode.getLinkSize(); k++) {
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.GREEN);
                        }
                    }
                    currentNode.setColor(Color.YELLOW);
                    g.repaint();

                    /* Sleep Screen */
                    try {
                        Thread.sleep(algorithmSpeed);
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /* Sleep Screen */

                    currentNode.setLinkColor(i, Color.BLACK);
                    currentNode.setColor(Color.WHITE);
                    for (int k=0; k<nextNode.getLinkSize(); k++) {
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.BLACK);
                        }
                    }
                    linksVisited++;
                }
                currentNode.setVisited(true);
            }
        }
        g.toggleGraphLock();
        System.out.println("Djikstra done");
        highlightShortestPath();
        g.repaint();
        AlgorithmSummary as = new AlgorithmSummary();
        as.showSummary(endNode.getCost(), linksVisited, nodeList);
        
    }

    public void printGraph() {
        for (int n=0; n<nodeList.size(); n++) {
            System.out.println("Name: "+nodeList.get(n).getName());
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                System.out.println("    "+"From: "+nodeList.get(n).getName()+", To: "+nodeList.get(n).getLinkTo(i).getName()+", Weight: "+nodeList.get(n).getWeight(i));
            }
        }
    }

    public void setAlgorithmSpeed(double speed) {
        algorithmSpeed = (int)(1000/speed);
    }

    private void highlightShortestPath() {
        Node currentNode = endNode;
        Node previousNode = endNode.getPreviousNode();
        while (currentNode != startNode) {
            currentNode.setColor(Color.MAGENTA);

            for (int i=0; i<previousNode.getLinkSize(); i++) {
                if (previousNode.getLinkTo(i) == currentNode) {
                    previousNode.setLinkColor(i, Color.MAGENTA);
                    for (int k=0; k<currentNode.getLinkSize(); k++) {
                        if (currentNode.getLinkTo(k) == previousNode) {
                            currentNode.setLinkColor(k, Color.MAGENTA);
                        }
                    }
                }
            }
            currentNode = previousNode;
            previousNode = previousNode.getPreviousNode();
            
        }
        currentNode.setColor(Color.MAGENTA);
        
    }
}
