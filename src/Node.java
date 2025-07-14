import java.util.ArrayList;
import java.awt.*;

public class Node {
    private String name;
    private int xPos;
    private int yPos;
    private Color colour;

    ArrayList<Weight> weightsList;

    public Node (String name, int xPos, int yPos) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.colour = Color.WHITE;
        weightsList = new ArrayList<Weight>();
    }
    public void createLink(int to, int weight) {
        weightsList.add(new Weight(to, weight));
    }
    public void deleteLink(int index) {
        weightsList.remove(index);
    }
    public void decreseWeightToNode(int n) {
        weightsList.get(n).decreaseToNode();
    }

    public void setX(int x) {
        this.xPos = x;
    }
    public void setY(int y) {
        this.yPos = y;
    }
    public void setColor(Color c) {
        this.colour = c;
    }
    public void setName(String n) {
        this.name = n;
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
    public Color getColor() {
        return this.colour;
    }
    public int getWeightSize() {
        return weightsList.size();
    }
    public int getWeightTo(int n) { 
        return weightsList.get(n).getToNode();
    }
    public int getWeightInt(int n) {
        return weightsList.get(n).getWeight();
    }
    
}
