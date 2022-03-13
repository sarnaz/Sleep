package sleepAppGUI.interaction;

import java.awt.*;

public class MyText extends VObject
{
    private String text;
    private String font = "Century Gothic";

    public MyText(Page page, int[] coordinates1, int[] coordinates2, String ttext)
    {
        super(page, coordinates1, coordinates2);
        text = ttext;
    }

    @Override
    void paint(Graphics g)
    {
        g.setFont(new Font(font, Font.PLAIN ,corner2[1] - corner1[1]));
        g.drawString(text, corner1[0], corner1[1]);
    }

    public void setText(String string) { text = string; }
}
