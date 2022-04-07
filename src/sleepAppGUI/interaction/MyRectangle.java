package sleepAppGUI.interaction;

import java.awt.*;

public class MyRectangle extends VObject {

    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private int arcX = 0;
    private int arcY = 0;
    private Color color;

    private static int[] getSecondCoordinates(int[] coordinates, int[] size) {
        return new int[] {coordinates[0] + size[0], coordinates[1] + size[1]};
    }

    public MyRectangle(Page page, int[] coordinates, int[] size, Color color) {
        super(page, coordinates, getSecondCoordinates(coordinates, size), true);
        this.width = size[0];
        this.height = size[1];
        this.x = coordinates[0];
        this.y = coordinates[1];
        this.color = color;
    }

    public MyRectangle(Page page, int[] coordinates, int[] size, int cornerRadius, Color color) {
        this(page, coordinates, size, new int[] {cornerRadius, cornerRadius}, color);
    }

    public MyRectangle(Page page, int[] coordinates, int[] size, int[] cornerRadius, Color color) {
        this(page, coordinates, size, color);
        this.arcX = cornerRadius[0];
        this.arcY = cornerRadius[1];
        this.color = color;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (this.color != null) {
            g2d.setColor(this.color);
            g2d.fillRoundRect(x, y, width, height, arcX, arcY);
        } else {
            g2d.drawRoundRect(x, y, width, height, arcX, arcY);
        }
    }
}
