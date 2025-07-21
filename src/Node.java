import java.util.ArrayList;
import java.awt.*;

public class Node {
    private String name;
    private int xPos;
    private int yPos;
    private Color colour;
    private int cost;
    private boolean visited;
    private Node previousNode;

    ArrayList<Link> linksList;

    public Node (String name, int xPos, int yPos) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.colour = Color.WHITE;
        linksList = new ArrayList<Link>();
    }
    public void createLink(int to, int weight) {
        linksList.add(new Link(to, weight));
    }
    public void deleteLink(int index) {
        linksList.remove(index);
    }
    public void decreseLinkToNode(int n) {
        linksList.get(n).decreaseLinkToNode();
    }
    public void setLinkWeight(int index, int weight) {
        linksList.get(index).setWeight(weight);
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
    public void setCost(int c) {
        this.cost = c;
    }
    public void setVisited(boolean v) {
        this.visited = v;
    }
    public void setPreviousNode(Node n) {
        this.previousNode = n;
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
    public int getLinkSize() {
        return linksList.size();
    }
    public int getLinkTo(int n) { 
        return linksList.get(n).getToNode();
    }
    public String getLinkToString(int n) { 
        return linksList.get(n).getToNodeString();
    }
    public int getWeightInt(int n) {
        return linksList.get(n).getWeight();
    }
    public boolean getVisted() {
        return this.visited;
    }
    public int getCost() {
        return this.cost;
    }
    
}
