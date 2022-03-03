package interaction;

import java.awt.*;

public abstract class VObject
{
    /*package private*/ int[] corner1;
    /*package private*/ int[] corner2;

    public VObject(Page page, int[] coordinate1, int[] coordinate2)
    {
        page.addObject(this);
        corner1 = coordinate1;
        corner2 = coordinate2;
    }

    /*package private*/ abstract void paint(Graphics g);
}
