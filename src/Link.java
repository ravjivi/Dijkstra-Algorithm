import java.awt.Color;
public class Link {
    private Node toNode;
    private int weight;
    private Color colour;

    public Link(Node to, int weight) {
        this.toNode = to;
        this.weight = weight;
        this.colour = Color.BLACK;
    }

    public void setWeight(int w) {
        this.weight = w;
    }
    public void setColor (Color c) {
        this.colour = c;
    }

    public Node getToNode() {
        return this.toNode;
    }
    public String getToNodeString() {
        return toNode.getName();
    }
    public int getWeight() {
        return this.weight;
    }
    public Color getColor() {
        return this.colour;
    }
}
