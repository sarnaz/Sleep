package sleepAppGUI.interaction;

import javax.swing.*;
import java.awt.*;

public class MyRectangle extends JFrame {

    public MyRectangle(int[] size) {

        setSize(size[0], size[1]);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRoundRect(10, 50, 150, 150, 50, 30); // to draw a rounded rectangle.
    }
}
