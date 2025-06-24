import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.event.*;



import java.util.ArrayList;

public class Graph extends JPanel {
    private final int NODERADIUS = 50;
    private final Color textColor = Color.BLACK;
    private final int textSize = 24;
    private String[] nodeNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    ArrayList<Node> nodesList = new ArrayList<Node>();
    private int draggedNodeIndex = -1;
    private int rClickedNodeIndex = -1;
    private JPopupMenu rClickMenu;
    private boolean makingWeight = false;
    
    
    Graph() {
        this.setBackground(new Color(60,60,60));
        rClickMenu = new JPopupMenu();
        JMenuItem infoItem1 = new JMenuItem("Create link");
        JMenuItem infoItem2 = new JMenuItem("Delete Node");
        
        rClickMenu.add(infoItem1);
        rClickMenu.add(infoItem2);
        infoItem1.addActionListener(e -> {
            System.out.println("Weight");
            makingWeight = true;
        });
        infoItem2.addActionListener(e -> {
            nodesList.remove(rClickedNodeIndex); // Delete Node
            repaint(); // Refresh canvas
        });
        // Add mouse listeners
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int nodeIndex = checkNodeHover(e.getX(), e.getY());
                if (nodeIndex != -1 && e.getButton() == 1 && makingWeight) { // Left click, node is hovered, and making link is true
                    int initialNode = rClickedNodeIndex;
                    int finalNode = nodeIndex;
                    drawWeight(initialNode, finalNode);
                }
                if (nodeIndex != -1 && e.getButton() == 1) { // Left click and node is hovered
                    draggedNodeIndex = nodeIndex;
                }
                
                if (e.getButton() == 3 && nodeIndex != -1) {  // Right click and node is hovered
                    rClickedNodeIndex = nodeIndex;
                    rClickMenu.show(Graph.this, e.getX(), e.getY()); 
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNodeIndex = -1;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedNodeIndex != -1) {
                    nodesList.get(draggedNodeIndex).setX(e.getX() - NODERADIUS);
                    nodesList.get(draggedNodeIndex).setY(e.getY() - NODERADIUS);
                    repaint();
                }
            }
            public void mouseMoved(MouseEvent e) {
                checkNodeHover(e.getX(), e.getY());
            }
        });
        
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;

        for (int n=0; n<nodesList.size(); n++) {
            for (int i=0; i<nodesList.get(n).getWeightSize(); i++) {
                g2d.setStroke(new BasicStroke(5));
                g2d.draw(new Line2D.Float(nodesList.get(n).getX()+NODERADIUS, nodesList.get(n).getY()+NODERADIUS , nodesList.get(nodesList.get(n).getWeightTo(n)).getX()+NODERADIUS, nodesList.get(nodesList.get(n).getWeightTo(n)).getY()+NODERADIUS));
            }
            paintNode(g2d, n);
        }
        
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

    public int checkNodeHover(int mouseX, int mouseY) {
        for (int n=0; n<nodesList.size(); n++) {
            double dx = mouseX - (nodesList.get(n).getX()+NODERADIUS); // Distance from centre of circle (x)
            double dy = mouseY - (nodesList.get(n).getY()+NODERADIUS); // Distance from centre of circle (y), +30 is for the window tab which isnt part of canvas
            double distance = Math.sqrt(dx * dx + dy * dy); // Calculating straight distance from centre using right angled triangle

            if (distance <= 50) { // If the distance is closer than the radius of the circle
                setNodeColour(n, Color.LIGHT_GRAY);
                repaint();
                return n;
            } else {
                setNodeColour(n, Color.WHITE);
                
            }
        }
        repaint();
        return -1;
    }

    public void drawWeight(int initialNode, int finalNode) {
        System.out.println("draw");
        nodesList.get(initialNode).createLink(finalNode, 10);
        makingWeight = false;
    }

    public void createNodeGraph() {
        nodesList.add(new Node(nodeNames[nodesList.size()], 50+(100*(nodesList.size())), 50));
        repaint();
    }

    public void setNodeColour(int n, Color c) {
        nodesList.get(n).setColor(c);
    }

}
