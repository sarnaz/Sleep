package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import java.util.Calendar;

public class AlcoholGraph extends GraphPage {

    @Override
    protected int pageNumber() {
        return 13;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        new MyImage(page, new int[] {300, 10}, new int[] {500, 100}, "exercise", true);
        MyBar alcohol_bar = new MyBar(page, new int[] {80,120}, new int[] {700,470});
        alcohol_bar.addPoint("Thursday",5.00);
        alcohol_bar.addPoint("Wednesday",3.00);
        alcohol_bar.addPoint("Friday",4.00);
        super.setUp(page);
    }
}
