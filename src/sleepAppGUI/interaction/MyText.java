package sleepAppGUI.interaction;

import java.awt.*;

public class MyText extends VObject
{
    public final static String defaultFont = "Helvetica";
    public final static int defaultStyle = Font.PLAIN;

    private String text;
    private String fontName = defaultFont;
    private Color colour = Color.white;
    private int style = defaultStyle;

    private boolean centre = false;

    public MyText(Page page, int[] coordinates1, int[] coordinates2, String text)
    {
        super(page, coordinates1, coordinates2, true);
        this.text = text;
    }

    public MyText(Page page, int[] coordinates1, int[] coordinates2, String text, Color textColour)
    {
        super(page, coordinates1, coordinates2, true);
        this.text = text;
        colour = textColour;
    }

    public MyText(Page page, int[] coordinates1, int[] coordinates2, String text, Color textColour, String fontName, int style)
    {
        super(page, coordinates1, coordinates2, true);
        this.text = text;
        this.fontName = fontName;
        this.style = style;
        this.colour = textColour;
    }

    public MyText(Page page, int[] coordinates1, int[] coordinates2, String text, Color textColour, String fontName, int style, boolean centre) {
        super(page, coordinates1, coordinates2, true);
        this.text = text;
        this.fontName = fontName;
        this.style = style;
        this.colour = textColour;
        this.centre = centre;
    }

    public static MyText putText(Page page, int[] coordinates1, int[] size, String text, Color textColour, String fontName, int style) {
        final int[] coordinates2 = new int[] {coordinates1[0] + size[0], coordinates1[1] + size[1]};

        return new MyText(page, coordinates1, coordinates2, text, textColour, fontName, style);
    }

    public static MyText putText(Page page, int[] coordinates1, int[] size, String text) {
        return putText(page, coordinates1, size, text, Color.black, defaultFont, defaultStyle);
    }

    public static MyText putText(Page page, int[] coordinates1, int[] size, String text, Color textColour) {
        return putText(page, coordinates1, size, text, textColour, defaultFont, defaultStyle);
    }

    public static MyText putTextCentred(Page page, int[] coordinateCentre, int[] size, String text, Color textColour, String fontName, int style) {
        final int[] coordinates2 = new int[] {coordinateCentre[0] + size[0], coordinateCentre[1] + size[1]};

        return new MyText(page, coordinateCentre, coordinates2, text, textColour, fontName, style, true);
    }
    public static MyText putTextCentred(Page page, int[] coordinateCentre, int[] size, String text, Color textColour) {
        return putTextCentred(page, coordinateCentre, size, text, textColour, defaultFont, defaultStyle);
    }

    @Override
    public void paint(Graphics g)
    {
        Font font = new Font(this.fontName, this.style, this.corner2[1] - this.corner1[1]);
        int[] topLeft = this.corner1.clone();
        if (this.centre) {
            FontMetrics fontMetrics = g.getFontMetrics(font);
            int renderedWidth = fontMetrics.stringWidth(this.text);
            topLeft[0] -= renderedWidth/2;
        }
        g.setFont(font);
        g.setColor(this.colour);
        g.drawString(this.text, topLeft[0], topLeft[1]);
    }

    public void setText(String string) { text = string; }
}
