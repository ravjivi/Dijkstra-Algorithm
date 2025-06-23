import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.event.*;

import org.w3c.dom.events.MouseEvent;

import java.util.ArrayList;

public class Graph extends JPanel {
    private final int NODERADIUS = 50;
    private final Color textColor = Color.BLACK;
    private final int textSize = 24;
    private String[] nodeNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    ArrayList<Node> nodesList = new ArrayList<Node>();
    
    
    Graph() {
        this.setBackground(new Color(60,60,60));
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;

        for (int n=0; n<nodesList.size(); n++) {
            paintNode(g2d, n);
        }

        //g2d.draw(new Line2D.Float(100 ,00, 100, 300));
    }

    private void paintNode(Graphics2D g2d, int node) {
        g2d.setColor(nodesList.get(node).getColor());
        g2d.fillOval(nodesList.get(node).getX(),nodesList.get(node).getY(),NODERADIUS*2, NODERADIUS*2);
        g2d.setColor(textColor);
        
        String text = nodesList.get(node).getName();
        Font font = new Font("Arial", Font.BOLD, 24);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int centreX = nodesList.get(node).getX() + NODERADIUS - (metrics.stringWidth(text)/2);  // Desired horizontal centre position
        int centreY = nodesList.get(node).getY() + NODERADIUS+(textSize/3);       // Desired vertical centre position

        g2d.drawString(text, centreX, centreY);
        
    }

    public void checkNodeHover(int mouseX, int mouseY) {
        for (int n=0; n<nodesList.size(); n++) {
            double dx = mouseX - (nodesList.get(n).getX()+NODERADIUS); // Distance from centre of circle (x)
            double dy = mouseY - (nodesList.get(n).getY()+NODERADIUS+30); // Distance from centre of circle (y), +30 is for the window tab which isnt part of canvas
            double distance = Math.sqrt(dx * dx + dy * dy); // Calculating straight distance from centre using right angled triangle

            if (distance <= 50) { // If the distance is closer than the radius of the circle
                setNodeColour(n, Color.LIGHT_GRAY);
            } else {
                setNodeColour(n, Color.WHITE);
                
            }
        }
        repaint();
    }

    public void createNodeGraph() {
        nodesList.add(new Node(nodeNames[nodesList.size()], 50+(100*(nodesList.size())), 50));
        repaint();
    }

    public void setNodeColour(int n, Color c) {
        nodesList.get(n).setColor(c);
    }

}
