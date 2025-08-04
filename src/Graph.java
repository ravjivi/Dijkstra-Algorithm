import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graph extends JPanel { 
    /* Class variables */
    private final Color textColor = Color.BLACK;
    private final int nodeRadius = 50;
    private int textSize = 24;
    private char[] nodeNames = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private ArrayList<Node> nodesList = new ArrayList<Node>(); // Arraylist to store nodes using node class
    private Node draggedNode, rClickedNode, startNode, endNode;
    private boolean makingWeight = false;
    private boolean hoveringLine = false;
    private int selectedLink[] = {0,0};
    private boolean lockGraph = false;
    private int mouseX, mouseY;
    
    Graph() {
        this.setBackground(new Color(60,60,60)); // Background colour
        // Add menu items to popup menu
        JPopupMenu rClickMenu = new JPopupMenu();
        JMenuItem infoItem1 = new JMenuItem("Create link");
        JMenuItem infoItem2 = new JMenuItem("Delete Node");
        JMenuItem infoItem3 = new JMenuItem("Make Start Node");
        JMenuItem infoItem4 = new JMenuItem("Make End Node");
        rClickMenu.add(infoItem1);
        rClickMenu.add(infoItem2);
        rClickMenu.add(infoItem3);
        rClickMenu.add(infoItem4);

        infoItem1.addActionListener(e -> { // Add link
            makingWeight = true;
        });
        infoItem2.addActionListener(e -> { // Delete Node
            deleteNode(rClickedNode);
            repaint(); // Refresh canvas
        });
        infoItem3.addActionListener(e -> { // Make this the new start node
            if (startNode != null) {
                startNode.setColor(Color.WHITE); // Set old start node to white
            }
            // Set new start node and make it green
            startNode = rClickedNode; 
            startNode.setColor(Color.GREEN);
            repaint(); // Refresh canvas
        });
        infoItem4.addActionListener(e -> { // Make this the new end node
            if (endNode != null) { // Set old end node to white
                endNode.setColor(Color.WHITE);
            }
            // Set new end node and make it red
            endNode = rClickedNode;
            endNode.setColor(Color.RED);
            repaint(); // Refresh canvas
        });
        // Add mouse listeners
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // When mouse button is clicked
                int nodeIndex = checkNodeHover(e.getX(), e.getY());
                if (nodeIndex != -1 && e.getButton() == 1 && makingWeight) { // Left click, node is hovered, and making link is true
                    rClickedNode.createLink(nodesList.get(nodeIndex), 10);
                    makingWeight = false;
                } else if (nodeIndex != -1 && e.getButton() == 1) { // Left click and node is hovered
                    draggedNode = nodesList.get(nodeIndex);
                } else if (e.getButton() == 1 && hoveringLine) { // Left click and hoving a link
                    setLinkWeight();
                }
                if (e.getButton() == 3 && nodeIndex != -1) {  // Right click and node is hovered
                    rClickedNode = nodesList.get(nodeIndex);
                    rClickMenu.show(Graph.this, e.getX(), e.getY()); 
                } 
                repaint(); // Refrest canvas
            }

            @Override
            public void mouseReleased(MouseEvent e) { // When mouse button is released
                draggedNode = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { // Mouse is moved while button is down
                if (draggedNode != null) { // The mouse is draggin on a node
                    // Move the position of dragged node
                    draggedNode.setX(e.getX() - nodeRadius);
                    draggedNode.setY(e.getY() - nodeRadius);
                    repaint();
                }
            }
            public void mouseMoved(MouseEvent e) { // Mouse is moved
                mouseX = e.getX();
                mouseY = e.getY();
                if (!lockGraph) {
                    checkNodeHover(mouseX, mouseY); // Check if mouse is hovering a node
                }
            }
        });
        
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;
        
        paintWeight(g2d); // Paint the links first
        paintNode(g2d); // Paint nodes over links
    }
    
    private void paintWeight(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(textSize/5));
        hoveringLine = false; // Reset hovering line

        for (int n=0; n<nodesList.size(); n++) { // For every node
            for (int i=0; i<nodesList.get(n).getLinkSize(); i++) { // For every link of particular node    
                // Centre the line in the middle of the node by adding node radius            
                int xInitial = nodesList.get(n).getX()+nodeRadius;  
                int yInitial = nodesList.get(n).getY()+nodeRadius;
                int xFinal = nodesList.get(n).getLinkTo(i).getX()+nodeRadius;
                int yFinal = nodesList.get(n).getLinkTo(i).getY()+nodeRadius;
                g2d.setColor(nodesList.get(n).getLinkColor(i)); // Set line colour
                g2d.draw(new Line2D.Float(xInitial, yInitial, xFinal, yFinal)); 

                double distL = Line2D.ptSegDist(xInitial, yInitial, xFinal, yFinal, mouseX, mouseY); // Function to check the distance between a line and a point
                if (distL <= 10.0) { // Within 10 pixels of the line 
                    hoveringLine = true; 
                    selectedLink[0] = n; // Store the node index
                    selectedLink[1] = i; // Store the link index
                }
                
                // Write the link weights in the centre of the line
                g2d.setColor(Color.WHITE);
                g2d.drawString(Integer.toString(nodesList.get(n).getWeight(i)), (xInitial+xFinal)/2, (yInitial+yFinal)/2); // Text, x-pos,y-pos
            }
            
        }
    }

    private void paintNode(Graphics2D g2d) {
        for (int n=0; n<nodesList.size(); n++) { // For every node
            g2d.setColor(nodesList.get(n).getColor()); // Set colour
            g2d.fillOval(nodesList.get(n).getX(),nodesList.get(n).getY(),nodeRadius*2, nodeRadius*2); // x-pos, y-pos, width, height
            
            g2d.setColor(textColor);
            String text = nodesList.get(n).getName();
            Font font = new Font("Arial", Font.BOLD, textSize); // To set text size
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font); // Get the size of the text
            // Set the postion of the text to it is centred in the node even with multiple characters (node AC)
            int centreX = nodesList.get(n).getX() + nodeRadius - (metrics.stringWidth(text)/2); // Desired horizontal centre position
            int centreY = nodesList.get(n).getY() + nodeRadius+(textSize/3); // Desired vertical centre position
    
            g2d.drawString(text, centreX, centreY); // Text, x-pos, y-pos
        }
    }

    public int checkNodeHover(int mouseX, int mouseY) {
        for (int n=0; n<nodesList.size(); n++) { // For every node
            double dx = mouseX - (nodesList.get(n).getX()+nodeRadius); // Distance of cursor from centre of circle (x)
            double dy = mouseY - (nodesList.get(n).getY()+nodeRadius); // Distance of curson from centre of circle (y)
            double distance = Math.sqrt(dx * dx + dy * dy); // Calculating straight distance from centre using right angled triangle

            if (distance <= 50) { // If the distance is closer than the radius of the circle, ie inside circle
                setNodeColour(n, Color.LIGHT_GRAY); // Make the node a darker shade
                repaint();
                return n; // Return hovering n index node
            } else { // Not hovering this node
                // Set node to default colour
                if (nodesList.get(n) == startNode) {
                    setNodeColour(n, Color.GREEN);
                } else if (nodesList.get(n) == endNode) {
                    setNodeColour(n, Color.RED);
                } else {
                    setNodeColour(n, Color.WHITE);
                }
            }
        }
        repaint();
        return -1; // -1 node does not exist so it tells the program nothing is hovered
    }

    public void createNodeGraph() {
        nodesList.add(new Node(returnNodeName(nodesList.size()), 50, 50)); // Add node with given parameters: name, x-pos, y-pos
        
        if (nodesList.getFirst() == nodesList.getLast()) { // If the first and the last node are the same node
            // Make start node and change colour
            startNode = nodesList.get(0);
            startNode.setColor(Color.GREEN);
        }
        repaint();
    }

    /**
     * This method is called 3 parameters, name, x-pos, y-pos
     * This method is called from CSV editor when it is creating a node with a pre-determined postion and name
     * It creates a new node
    */
    public void createNodeGraph(String name, int xPos, int yPos) {
        nodesList.add(new Node(name, xPos, yPos)); // New node to arraylist
        repaint();
    }

    public void setNodeColour(int n, Color c) {
        nodesList.get(n).setColor(c); // Change colour of node
    }
    
    private void deleteNode(Node deletedNode) { // Called after node is deleted to update names 
        for (int n=0; n<nodesList.size(); n++) {
            for (int i=0; i<nodesList.get(n).getLinkSize(); i++) {
                if (nodesList.get(n).getLinkTo(i) == deletedNode) { // If neigboring node as a link to the deleted node
                    nodesList.get(n).deleteLink(i); // Delete that link
                } 
            }
        }
        nodesList.remove(deletedNode); // Delete Node
        for (int i=nodesList.indexOf(deletedNode)+1; i<nodesList.size(); i++) { // For nodes with a higher index than the deleted node
            nodesList.get(i).setName(returnNodeName(i)); // Change the name of the node by 1(ie Q --> P)
        }
        
    } 

    private void setLinkWeight() {
        JDialog dialog = new JDialog(); // Create dialog window
        JTextField weightTextField = new JTextField(Integer.toString(10), 2); // Editable text field
        dialog.setTitle("Weight Edit");
        dialog.setSize(250, 100); // Size
        dialog.setLocationRelativeTo(null); // Center on screen
        dialog.add(new JLabel("Enter the new value of the weight", SwingConstants.CENTER), BorderLayout.NORTH); // Add text to dilog
        dialog.add(weightTextField, BorderLayout.CENTER); // Add text field to dialog
        dialog.setModal(true); // Block input to other windows
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose()); // Close dialog when button is ok button is clicked

        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.getRootPane().setDefaultButton(okButton); // Closes the dialog box when Enter key is pressed
        dialog.setVisible(true);
        try {
            int weight = Integer.parseInt(weightTextField.getText());
            if (weight > 0) {
                nodesList.get(selectedLink[0]).setLinkWeight(selectedLink[1], weight);
            } else {
                throwError("Invalid weight value. Please enter a number greater than 0.");
            }
            
        } catch (NumberFormatException e) {
            throwError("Invalid weight value. Please enter a number.");
        }
    }

    public void toggleGraphLock() {
        if (lockGraph) {
            lockGraph = false;
        } else {
            lockGraph = true;
        }
    }

    public void throwError(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearAllNode() {
        nodesList.clear();
        repaint();
    }

    private String returnNodeName(int index) {
        String nodeName;
        char secondLetter = nodeNames[index%26];
        if (index < 26) {
            nodeName = ""+secondLetter;
        } else {
            char firstLetter = nodeNames[(int)Math.floor(index/26)-1];
            nodeName = ""+firstLetter+secondLetter;
        }
        return nodeName;
    }

    public void setStartNode(Node n) {
        startNode = n;
    }
    public void setEndNode(Node n) {
        endNode = n;
    }
    public Node getStartNode() {
        return startNode;
    }
    public Node getEndNode() {
        return endNode;
    }
    public ArrayList<Node> getNodesList() {
        return nodesList;
    }  
}
