import java.util.ArrayList;
public class Algorithm {
    private ArrayList<Node> nodeList;
    public Algorithm (Graph graph) {
        nodeList = graph.getNodesList();
        setupAlgorithm();
    }

    public void setupAlgorithm() {
        for (int n=0; n<nodeList.size(); n++) {
            System.out.println("Name: "+nodeList.get(n).getName());
            nodeList.get(n).setCost(Integer.MAX_VALUE);
            nodeList.get(n).setVisited(false);
            for (int i=0; i<nodeList.get(n).getLinkSize(); i++) {
                System.out.println("    "+"From: "+nodeList.get(n).getName()+", To: "+nodeList.get(n).getLinkToString(i)+", Weight: "+nodeList.get(n).getWeightInt(i));
            }
        }
    }
}
