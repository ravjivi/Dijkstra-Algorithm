import java.util.ArrayList;
import java.awt.*;

public class Node {
    /* Object variables */
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
    public void createLink(Node to, int weight) {
        boolean exists = false;
        for (int i=0; i<linksList.size(); i++) { // Check every current link
            if (linksList.get(i).getToNode() == to) { // If the imposed new link already exists 
                exists = true;
                break; 
            }
        }
        if (!exists) { // If the imposed new link doesn't exists 
            linksList.add(new Link(to, weight));
        } 
    }
    public void deleteLink(int index) {
        linksList.remove(index); // Remove from array list
    }

    public void setLinkWeight(int index, int weight) {
        linksList.get(index).setWeight(weight); // Set the new link weight
        for (int i=0; i<linksList.get(index).getToNode().getLinkSize(); i++) {
            // Check if there is a same link in the opposite direction
            if (linksList.get(index).getToNode().getLinkToString(i).equals(this.name) && linksList.get(index).getToNode().getWeight(i) != linksList.get(index).getWeight()) { 
                linksList.get(index).getToNode().setLinkWeight(0, weight);  
            }
        }        
    }
    /* Setters */
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
    public void setLinkColor (int n, Color c) {
        linksList.get(n).setColor(c);
    }
    
    /* Getters */
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
    public Node getLinkTo(int n) { 
        return linksList.get(n).getToNode();
    }
    public String getLinkToString(int n) { 
        return linksList.get(n).getToNodeString();
    }
    public int getWeight(int n) {
        return linksList.get(n).getWeight();
    }
    public boolean getVisted() {
        return this.visited;
    }
    public int getCost() {
        return this.cost;
    }
    public Color getLinkColor (int n) {
        return linksList.get(n).getColor();
    }
    public Node getPreviousNode() {
        return this.previousNode;
    }
    
}
