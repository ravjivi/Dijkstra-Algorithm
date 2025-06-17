import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;

class Graphic extends JPanel {
    private int nNodes = 0;

    Graphic() {
       this.setBackground(Color.white);
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.RED);
        for (int n=0; n<nNodes; n++) {
            g2d.fillOval(50+(n*100),50,100,100);
        }

        //g2d.draw(new Line2D.Float(50 ,50, 300, 300));
   
    }

    public void createNodeGraphic() {
        nNodes++;
        repaint();
    }

    public int getNNodes() {
        return nNodes;
    }

}
