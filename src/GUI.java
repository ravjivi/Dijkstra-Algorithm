import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenu menuEdit;
    private JMenuItem newGraph;
    private JMenuItem openGraph;
    private JMenuItem saveGraph;
    private JMenuItem newNode;
    private Graphic g;

    public GUI() {
        
    }

    public void createWindow() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("Dijkstra's Algorithm");
        this.getContentPane().setPreferredSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.pack();
        this.toFront();
        this.setVisible(true);
    }

    public void addElements() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        newGraph = new JMenuItem("New Graph");
        openGraph = new JMenuItem("Open Graph");
        saveGraph = new JMenuItem("Save Graph");
        newNode = new JMenuItem("New Node");
        
        menuFile.add(newGraph);
        menuFile.add(openGraph);
        menuFile.add(saveGraph);
        menuEdit.add(newNode);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
       
        menuBar.setBackground(Color.ORANGE);

        g = new Graphic();
        this.add(g, BorderLayout.CENTER);

        this.setJMenuBar(menuBar);
        
    }
}
