import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.ArrayList;

public class Graph extends JPanel {
    private final int NODERADIUS = 50;
    private final Color nodeColor = Color.WHITE;
    private final Color textColor = Color.BLACK;
    private final int textSize = 24;
    private int nNodes = 0;
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
            g2d.setColor(nodeColor);
            g2d.fillOval(nodesList.get(n).getX(),nodesList.get(n).getY(),NODERADIUS*2, NODERADIUS*2);
            g2d.setColor(textColor);
            
            String text = nodesList.get(n).getName();
            Font font = new Font("Arial", Font.BOLD, 24);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            int centerX = nodesList.get(n).getX() + NODERADIUS - (metrics.stringWidth(text)/2);  // Desired horizontal center position
            int centerY = nodesList.get(n).getY() + NODERADIUS+(textSize/3);       // Vertical position (baseline of text)

            g.drawString(text, centerX, centerY);
        }

        //g2d.draw(new Line2D.Float(100 ,00, 100, 300));
    }

    public void createNodeGraph() {
        nodesList.add(new Node(nodeNames[nodesList.size()], 50+(100*(nodesList.size())), 50));
        repaint();
    }

    public int getNNodes() {
        return nNodes;
    }

}
