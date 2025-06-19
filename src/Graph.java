import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.ArrayList;

public class Graph extends JPanel {
    private int nNodes = 0;
    private String[] nodeNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    ArrayList<Node> nodesList = new ArrayList<Node>();
    
    
    Graph() {
       this.setBackground(Color.white);
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.RED);
        for (int n=0; n<nodesList.size(); n++) {
            g2d.fillOval(nodesList.get(n).getX(),nodesList.get(n).getY(),100,100);
        }

        //g2d.draw(new Line2D.Float(50 ,50, 300, 300));
   
    }

    public void createNodeGraph() {
        nodesList.add(new Node(nodeNames[nodesList.size()], 50+(100*(nodesList.size())), 50));
        repaint();
    }

    public int getNNodes() {
        return nNodes;
    }

}
