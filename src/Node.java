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
    public Color getColor() {
        return this.colour;
    }

    public void setColor(Color c) {
        this.colour = c;
    }
}
