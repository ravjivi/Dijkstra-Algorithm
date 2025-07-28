import java.awt.Color;
import java.util.ArrayList;
public class Algorithm {
    private ArrayList<Node> nodeList;
    private Node startNode;

    public Algorithm (Graph g) {
        nodeList = g.getNodesList();
        setupAlgorithm();
        twoWayLinks();
        printGraph();
        runDijkstra(g);
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

    public void setupAlgorithm() {
        for (int n=0; n<nodeList.size(); n++) {
            if (nodeList.get(n).getColor() == Color.BLUE) { // If node is start node
                nodeList.get(n).setCost(0);
                startNode = nodeList.get(n);
            } else {
                nodeList.get(n).setCost(Integer.MAX_VALUE);
            }
            nodeList.get(n).setVisited(false);
        }
    }

    public void runDijkstra(Graph g) {
        g.toggleGraphLock();
        Queue queue = new Queue();
        queue.enqueue(startNode);

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

                    System.out.println("stop");
                    currentNode.setLinkColor(i, Color.GREEN);
                    for (int k=0; k<nextNode.getLinkSize(); k++) {
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.GREEN);
                        }
                    }
                    currentNode.setColor(Color.YELLOW);
                    g.repaint();
                    try {
                        Thread.sleep(1000);
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("start");
                    currentNode.setLinkColor(i, Color.BLACK);
                    currentNode.setColor(Color.WHITE);
                    for (int k=0; k<nextNode.getLinkSize(); k++) {
                        if (nextNode.getLinkTo(k) == currentNode) {
                            nextNode.setLinkColor(k, Color.BLACK);
                        }
                    }
                }
                currentNode.setVisited(true);
            }
        }
        g.toggleGraphLock();
        System.out.println("Djikstra done");
    }

    public void printGraph() {
        for (int n=0; n<nodeList.size(); n++) {
            System.out.println("Name: "+nodeList.get(n).getName());
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                System.out.println("    "+"From: "+nodeList.get(n).getName()+", To: "+nodeList.get(n).getLinkTo(i).getName()+", Weight: "+nodeList.get(n).getWeight(i));
            }
        }
    }
}
