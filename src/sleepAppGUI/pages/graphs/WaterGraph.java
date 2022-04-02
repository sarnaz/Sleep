package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;

public class WaterGraph extends GraphPage {
    @Override
    protected int pageNumber() {
        return 0;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        MyImage water_frame = new MyImage(page, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(page, new int[] {300, 10}, new int[] {500, 100}, "water", true);

        super.setUp(page);
    }
}
