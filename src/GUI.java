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
    private Graph g;

    private final Color menuBarColor = new Color(50,50,50);
    private final Color menuItemColor = Color.WHITE;

    private CSVEditor csv = new CSVEditor();

    public GUI(String os) {
        createWindow();
        if (os.equals("windows")) {
            newNode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        } else if (os.equals("mac")) {
            newNode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.META_DOWN_MASK));
        }
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
        g = new Graph();

        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder());
	    menuBar.setBackground(menuBarColor);
        menuFile = new JMenu("File");
	    menuFile.setForeground(menuItemColor);
        menuEdit = new JMenu("Edit");
	    menuEdit.setForeground(menuItemColor);
        menuRun = new JMenu("Run");
        menuRun.setForeground(menuItemColor);
        
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

        menuFile.add(newGraph);
        menuFile.add(openGraph);
        menuFile.add(saveGraph);
        menuEdit.add(newNode);
        menuRun.add(runDijkstra);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuRun);

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
                csv.readCSV(g);
                break;
            case "Save Graph":
                csv.writeCSV(g);
                break;
            case "New Node":
                g.createNodeGraph();
                System.out.println("");
                break;
            case "Run Dijkstra's Algorithm":    
                System.out.println("running Dijkstra's Algorithm");
                new Algorithm(g);
            default:
                break;
        }
    }

    public Graph returnGraph() {
        return g;
    }
}
