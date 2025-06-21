import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;  


public class GUI extends JFrame implements ActionListener {
    /* Class Variables */
    private final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get device screen size
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenuItem newGraph;
    private JMenuItem openGraph;
    private JMenuItem saveGraph;
    private JMenuItem newNode;
    private Graph g;

    private final Color menuBarColor = new Color(50,50,50);
    private final Color menuItemColor = Color.WHITE;

    public GUI() {
        createWindow();
    }

    private void createWindow() {
        this.setTitle("Dijkstra's Algorithm");
        this.getContentPane().setPreferredSize(new Dimension((int)SCREENSIZE.getWidth()*3/4, (int)SCREENSIZE.getHeight()*3/4)); // Make window size 3/4 the screen size
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addElements();
        this.pack();
        this.setLocationRelativeTo(null);  // Centres the screen
        this.toFront();
        this.setVisible(true);
    }

    private void addElements() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder());
	    menuBar.setBackground(menuBarColor);
        menuFile = new JMenu("File");
	    menuFile.setForeground(menuItemColor);
        menuEdit = new JMenu("Edit");
	    menuEdit.setForeground(menuItemColor);
        newGraph = new JMenuItem("New Graph");
        newGraph.addActionListener(this);
        openGraph = new JMenuItem("Open Graph");
        openGraph.addActionListener(this);
        saveGraph = new JMenuItem("Save Graph");
        saveGraph.addActionListener(this);
        newNode = new JMenuItem("New Node");
        newNode.addActionListener(this);
        menuFile.add(newGraph);
        menuFile.add(openGraph);
        menuFile.add(saveGraph);
        menuEdit.add(newNode);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        
        g = new Graph();
        this.add(g, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "New Graph":
                System.out.println("Create new graph");
                break;
            case "Open Graph":
                System.out.println("Opening new graph");
                break;
            case "Save Graph":
                break;
            case "New Node":
                g.createNodeGraph();
                break;
            default:
                break;
        }

    }
}
