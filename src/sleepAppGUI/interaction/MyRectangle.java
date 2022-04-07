package sleepAppGUI.interaction;

import javax.swing.*;
import java.awt.*;

public class MyRectangle extends JFrame {

    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private int arcX = 0;
    private int arcY = 0;

    public MyRectangle(int[] coordinates, int[] size) {
        this.width = size[0];
        this.height = size[1];
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    public MyRectangle(int[] coordinates, int[] size, int cornerRadius) {
        this(coordinates, size, new int[] {cornerRadius, cornerRadius});
    }

    public MyRectangle(int[] coordinates, int[] size, int[] cornerRadius) {
        this(coordinates, size);
        this.arcX = cornerRadius[0];
        this.arcY = cornerRadius[1];
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRoundRect(x, y, width, height, arcX, arcY);
    }
}
