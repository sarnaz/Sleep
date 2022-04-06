package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;

public class ScreenTimeGraph extends GraphPage{

    @Override
    protected int pageNumber() {
        return 14;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        new MyImage(page, new int[] {300, 10}, new int[] {500, 100}, "screentime", true);

        super.setUp(page);
    }
}
