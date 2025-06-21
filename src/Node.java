import java.util.ArrayList;
public class Node {
    private String name;
    private int xPos;
    private int yPos;

    ArrayList<Weight> weightsList;
    public Node() {
        this.xPos = 50;
        this.yPos = 50;
    }

    public Node (String name, int xPos, int yPos) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        weightsList = new ArrayList<Weight>();
    }
    public void createLink(String to, int weight) {
        weightsList.add(new Weight(this.name, to, weight));
    }

    public String getName() {
        return this.name;
    }
    public int getX() {
        return this.xPos;
    }
    public int getY() {
        return this.yPos;
    }
}
