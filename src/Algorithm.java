import java.awt.Color;
import java.util.ArrayList;
public class Algorithm {
    private ArrayList<Node> nodeList;
    private Node startNode;

    public Algorithm (Graph graph) {
        nodeList = graph.getNodesList();
        setupAlgorithm();
        twoWayLinks();
        printGraph();
        runDijkstra();
    }

    public void twoWayLinks() {
        for (int n=0; n<nodeList.size(); n++) {
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                Node thisNode = nodeList.get(n);
                Node toNode = nodeList.get(thisNode.getLinkTo(i));
                if (!checkNodeLink(toNode, n)) {
                    toNode.createLink(n, thisNode.getWeightInt(i));
                }            
            }
        }
    }
    private boolean checkNodeLink(Node toNode, int n) {
        for (int k=0; k<toNode.getLinkSize(); k++) {
            if (toNode.getLinkTo(k) == n) {
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

    public void runDijkstra() {
        PQueue queue = new PQueue();
        queue.enqueue(startNode,false);

        while (!queue.isEmpty()) {
            Node currentNode = queue.dequeue();
            if (!currentNode.getVisted()) {
                for (int i=0; i<currentNode.getLinkSize(); i++) {
                    int thisCost = currentNode.getCost() + currentNode.getWeightInt(i);
                    Node nextNode = nodeList.get(currentNode.getLinkTo(i));
                    if (thisCost < nextNode.getCost()) {
                        nextNode.setCost(thisCost);
                        nextNode.setPreviousNode(currentNode);
                        queue.enqueue(nextNode, true);
                    }
                }
                currentNode.setVisited(true);
            }
        }
        System.out.println("Djikstra done");
    }

    public void printGraph() {
        for (int n=0; n<nodeList.size(); n++) {
            System.out.println("Name: "+nodeList.get(n).getName());
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                System.out.println("    "+"From: "+nodeList.get(n).getName()+", To: "+nodeList.get(n).getLinkToString(i)+", Weight: "+nodeList.get(n).getWeightInt(i));
            }
        }
    }
}
