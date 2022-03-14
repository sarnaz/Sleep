package sleepAppGUI.interaction;

import java.awt.*;

public abstract class VObject
{
    protected int[] corner1;
    protected int[] corner2;

    private boolean visible;
    private final boolean defVis;

    public VObject(Page page, int[] coordinate1, int[] coordinate2, boolean defaultVisible)
    {
        page.addObject(this);
        corner1 = coordinate1;
        corner2 = coordinate2;
        defVis = defaultVisible;
        visible = defVis;
    }

    public abstract void paint(Graphics g);

    public boolean isVisible() { return visible; }
    public void setVisible(boolean vis) { visible = vis; }
    public boolean getDefVis() { return defVis; }
}
