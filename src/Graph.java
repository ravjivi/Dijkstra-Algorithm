import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graph extends JPanel {
    private int nodeRadius = 50;
    private final Color textColor = Color.BLACK;
    private int textSize = 24;
    private String[] nodeNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
    "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    private ArrayList<Node> nodesList = new ArrayList<Node>();
    private int draggedNodeIndex = -1;
    private int rClickedNodeIndex = -1;
    private boolean makingWeight = false;
    private JTextField weightTextField = new JTextField(Integer.toString(10), 2);
    private boolean hoveringLine = false;
    private int selectedLink[] = {0,0};
    private int startNodeIndex = 0;
    int mouseX;
    int mouseY;
    
    
    
    Graph() {
        this.setBackground(new Color(60,60,60));
        JPopupMenu rClickMenu = new JPopupMenu();
        JMenuItem infoItem1 = new JMenuItem("Create link");
        JMenuItem infoItem2 = new JMenuItem("Delete Node");
        JMenuItem infoItem3 = new JMenuItem("Make Start Node");
        
        rClickMenu.add(infoItem1);
        rClickMenu.add(infoItem2);
        rClickMenu.add(infoItem3);
        infoItem1.addActionListener(e -> { // Add link
            System.out.println("Weight");
            makingWeight = true;
        });
        infoItem2.addActionListener(e -> { // Delete Node
            updateNodes(rClickedNodeIndex);
            repaint(); // Refresh canvas
        });
        infoItem3.addActionListener(e -> { // Delete Node
            nodesList.get(rClickedNodeIndex).setColor(Color.BLUE);
            startNodeIndex = rClickedNodeIndex;
            repaint(); // Refresh canvas
        });
        // Add mouse listeners
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int nodeIndex = checkNodeHover(e.getX(), e.getY());
                if (nodeIndex != -1 && e.getButton() == 1 && makingWeight) { // Left click, node is hovered, and making link is true
                    nodesList.get(rClickedNodeIndex).createLink(nodeIndex, 10);
                    makingWeight = false;
                } else if (nodeIndex != -1 && e.getButton() == 1) { // Left click and node is hovered
                    draggedNodeIndex = nodeIndex;
                } else if (e.getButton() == 1 && hoveringLine) {
                    setLinkWeight();
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
                    nodesList.get(draggedNodeIndex).setX(e.getX() - nodeRadius);
                    nodesList.get(draggedNodeIndex).setY(e.getY() - nodeRadius);
                    repaint();
                }
            }
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                checkNodeHover(mouseX, mouseY);
            }
        });
        
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;
        
        paintWeight(g2d);
        paintNode(g2d);
    }
    
    private void paintWeight(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(textSize/5));
        hoveringLine = false; // Reset hovering line

        for (int n=0; n<nodesList.size(); n++) {
            for (int i=0; i<nodesList.get(n).getLinkSize(); i++) {
                //System.out.println("n: "+n);
                //System.out.println("i: "+i);
               
                int xInitial = nodesList.get(n).getX()+nodeRadius;  
                int yInitial = nodesList.get(n).getY()+nodeRadius;
                int xFinal = nodesList.get(nodesList.get(n).getLinkTo(i)).getX()+nodeRadius;
                int yFinal = nodesList.get(nodesList.get(n).getLinkTo(i)).getY()+nodeRadius;
                g2d.setColor(Color.BLACK);
                g2d.draw(new Line2D.Float(xInitial, yInitial, xFinal, yFinal));

                double distL = Line2D.ptSegDist(xInitial, yInitial, xFinal, yFinal, mouseX, mouseY); // Function to check the distance between a line and a point
                if (distL <= 10.0) { // Within 10 pixels of the line 
                    hoveringLine = true; 
                    selectedLink[0] = n; // Store the node index
                    selectedLink[1] = i; // Store the link index
                }
                    
                g2d.setColor(Color.WHITE);
                g2d.drawString(Integer.toString(nodesList.get(n).getWeightInt(i)), (xInitial+xFinal)/2, (yInitial+yFinal)/2);
            }
            
        }
    }

    private void paintNode(Graphics2D g2d) {
        for (int n=0; n<nodesList.size(); n++) {
            g2d.setColor(nodesList.get(n).getColor());
            g2d.fillOval(nodesList.get(n).getX(),nodesList.get(n).getY(),nodeRadius*2, nodeRadius*2);
            g2d.setColor(textColor);
            
            String text = nodesList.get(n).getName();
            Font font = new Font("Arial", Font.BOLD, textSize);
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);
            int centreX = nodesList.get(n).getX() + nodeRadius - (metrics.stringWidth(text)/2);  // Desired horizontal centre position
            int centreY = nodesList.get(n).getY() + nodeRadius+(textSize/3);       // Desired vertical centre position
    
            g2d.drawString(text, centreX, centreY);
        }
    }

    public int checkNodeHover(int mouseX, int mouseY) {
        for (int n=0; n<nodesList.size(); n++) {
            double dx = mouseX - (nodesList.get(n).getX()+nodeRadius); // Distance from centre of circle (x)
            double dy = mouseY - (nodesList.get(n).getY()+nodeRadius); // Distance from centre of circle (y), +30 is for the window tab which isnt part of canvas
            double distance = Math.sqrt(dx * dx + dy * dy); // Calculating straight distance from centre using right angled triangle

            if (distance <= 50) { // If the distance is closer than the radius of the circle
                setNodeColour(n, Color.LIGHT_GRAY);
                repaint();
                return n;
            } else {
                if (n == startNodeIndex) {
                    setNodeColour(n, Color.BLUE);
                } else {
                    setNodeColour(n, Color.WHITE);
                }
                
            }
        }
        repaint();
        return -1;
    }

    public void createNodeGraph() {
        nodesList.add(new Node(nodeNames[nodesList.size()], 50+(100*(nodesList.size())), 50));
        repaint();
    }
    public void createNodeGraph(String name, int xPos, int yPos) {
        nodesList.add(new Node(name, xPos, yPos));
        repaint();
    }

    public void setNodeColour(int n, Color c) {
        nodesList.get(n).setColor(c);
    }

    private void updateNodes(int deletedNode) { // Called after node is deleted to update names 
        for (int n=0; n<nodesList.size(); n++) {
            for (int i=0; i<nodesList.get(n).getLinkSize(); i++) {
                if (nodesList.get(n).getLinkTo(i) == deletedNode) {
                    nodesList.get(n).deleteLink(i);
                } else if (nodesList.get(n).getLinkTo(i) > deletedNode) {
                    nodesList.get(n).decreseLinkToNode(i);
                }
            }
        }
        nodesList.remove(deletedNode); // Delete Node
        for (int i=rClickedNodeIndex; i<nodesList.size(); i++) {
            nodesList.get(i).setName(nodeNames[i]);
        }
    }

    public void changeNodeSize(double multiplyer) {
        nodeRadius = (int)Math.floor(50*multiplyer);
        textSize = (int)Math.floor(24*multiplyer);
        repaint();
    }
    private void setLinkWeight() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Weight Edit");
        dialog.setSize(250, 100);
        dialog.setLocationRelativeTo(null); // Center on screen
        dialog.add(new JLabel("Enter the new value of the weight", SwingConstants.CENTER), BorderLayout.NORTH);
        dialog.add(weightTextField, BorderLayout.CENTER);
        dialog.setModal(true); // Block input to other windows
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.getRootPane().setDefaultButton(okButton); // Closes the dialog box when Enter is pressed
        dialog.setVisible(true);
        try {
            int weight = Integer.parseInt(weightTextField.getText());
            nodesList.get(selectedLink[0]).setLinkWeight(selectedLink[1], weight);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid weight value. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ArrayList<Node> getNodesList() {
        return nodesList;
    }
}
