import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;

class Graphic extends JPanel {

    Graphic() {
       this.setBackground(Color.white);
    }

    public void paint(Graphics g) {
        super.paint(g); // Recall the method to refresh screen
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.RED);
        g2d.fillOval(50,50,100,100);
        g2d.setColor(Color.BLACK);
        g2d.draw(new Line2D.Float(50 ,50, 300, 300));
    }

}
