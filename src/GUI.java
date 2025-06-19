import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  


public class GUI extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenuItem newGraph;
    private JMenuItem openGraph;
    private JMenuItem saveGraph;
    private JMenuItem newNode;
    private Graph g;

    public GUI() {
        createWindow();
    }

    public void createWindow() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Dijkstra's Algorithm");
        this.getContentPane().setPreferredSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addElements();
        this.pack();
        this.toFront();
        this.setVisible(true);
    }

    public void addElements() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
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
       
        menuBar.setBackground(Color.ORANGE);

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
