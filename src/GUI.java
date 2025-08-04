import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
    /* Class Variables */
    private final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get device screen size
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenu menuRun;
    private JMenuItem newGraph;
    private JMenuItem openGraph;
    private JMenuItem saveGraph;
    private JMenuItem newNode;
    private JMenuItem runDijkstra;
    private JMenu animationSpeed;
    private JRadioButtonMenuItem[] algorithmSpeedItems;
    private Graph g;
    private Algorithm a;

    private final Color menuBarColor = new Color(50,50,50);
    private final Color menuItemColor = Color.WHITE;

    private CSVEditor csv = new CSVEditor();

    public GUI(String os) {
        createWindow();
        if (os.equals("windows")) {
            // Set windows keyboard shortcuts
            newNode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
            newGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
            openGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
            saveGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        } else if (os.equals("mac")) {
            // Set mac keyboard shortcuts
            newNode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.META_DOWN_MASK));
            newGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.META_DOWN_MASK));
            openGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.META_DOWN_MASK));
            saveGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_DOWN_MASK));
        }
    }

    private void createWindow() {
        this.setTitle("Dijkstra's Algorithm");
        this.getContentPane().setPreferredSize(new Dimension((int)SCREENSIZE.getWidth()*3/4, (int)SCREENSIZE.getHeight()*3/4)); // Make window size 3/4 the screen size
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Kill program when window is closed
        
        addElements(); // Add components to GUI

        this.pack();
        this.setLocationRelativeTo(null);  // Centres the screen
        this.toFront();
        this.setVisible(true);
    }

    private void addElements() {
        g = new Graph();
        a = new Algorithm(g);

        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder()); // Remove margin arond menu bar
	    menuBar.setBackground(menuBarColor);
        // Set name and colour of menu headers
        menuFile = new JMenu("File");
	    menuFile.setForeground(menuItemColor);
        menuEdit = new JMenu("Edit");
	    menuEdit.setForeground(menuItemColor);
        menuRun = new JMenu("Run");
        menuRun.setForeground(menuItemColor);

        // Create button group for speed control and set the values
        ButtonGroup startGroup = new ButtonGroup();
        algorithmSpeedItems = new JRadioButtonMenuItem[5];
        for (int i=0; i<5; i++) {
            algorithmSpeedItems[i] = new JRadioButtonMenuItem(Double.toString(Math.pow(2, i)*0.5)+"x");
            algorithmSpeedItems[i].addActionListener(this);
            startGroup.add(algorithmSpeedItems[i]);
        }
        algorithmSpeedItems[1].setSelected(true); // Default selected button is 1x
        
        // Set Menu items names and action listener 
        newGraph = new JMenuItem("New Graph");
        newGraph.addActionListener(this);
        openGraph = new JMenuItem("Open Graph");
        openGraph.addActionListener(this);
        saveGraph = new JMenuItem("Save Graph");
        saveGraph.addActionListener(this);
        newNode = new JMenuItem("New Node");
        newNode.addActionListener(this);
        runDijkstra = new JMenuItem("Run Dijkstra's Algorithm");
        runDijkstra.addActionListener(this);
        animationSpeed = new JMenu("Animation Speed");
        for (int i=0; i<algorithmSpeedItems.length; i++) {
            animationSpeed.add(algorithmSpeedItems[i]);
        }

        // Add menu conmponets to their appropirate parent
        menuFile.add(newGraph);
        menuFile.add(openGraph);
        menuFile.add(saveGraph);
        menuEdit.add(newNode);
        menuRun.add(runDijkstra);
        menuRun.add(animationSpeed);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuRun);

        this.add(g, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
    }

    /**
     * This method is called whenever any button is clicked
     * It checks what button was clicked then performs its relevant action
    */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) { // Determines result based off input action
            case "New Graph":
                System.out.println("Create new graph");
                g.clearAllNode(); // Delete all nodes and llinks
                break;
            case "Open Graph":
                System.out.println("Opening graph from CSV");
                g.clearAllNode(); // Delete all nodes and links
                csv.readCSV(g); // Open graph from CSV
                break;
            case "Save Graph":
                System.out.println("Saving graph");
                if (g.getStartNode() != null && g.getEndNode() != null && g.getStartNode() != g.getEndNode()) { // Graph has start and end node and they are not the same
                    csv.writeCSV(g); // Write current graph to CSV
                } else {
                    g.throwError("Please select a start and end node by right clicking on nodes"); // Open error dialogue
                }
                break;
            case "New Node":
                System.out.println("Creating new node");
                g.createNodeGraph(); // Add 1 node
                break;
            case "Run Dijkstra's Algorithm":  
                if (g.getStartNode() != null && g.getEndNode() != null && g.getStartNode() != g.getEndNode()) { // Graph has start and end node and they are not the same
                    System.out.println("running Dijkstra's Algorithm");
                    new Thread(() -> { // Run algorithm on new thread for thread.sleep later
                        a.setupAlgorithm(g); // Start algorithm
                    }).start();
                }  else {
                    g.throwError("Please select a start and end node by right clicking on nodes"); // Open error dialogue
                }
            default:
                break;
        }
        // Check if button press was either of the button group items
        for (int i=0; i<algorithmSpeedItems.length; i++) {
            if (cmd == algorithmSpeedItems[i].getText()) {
                System.out.println("Changing algorithm speed to: "+algorithmSpeedItems[i].getText());
                a.setAlgorithmSpeed(Math.pow(2, i)*0.5); // Change algorithm speed
            }
        }
    }

    public Graph returnGraph() {
        return g;
    }
}
