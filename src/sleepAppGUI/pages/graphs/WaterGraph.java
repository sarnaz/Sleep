package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.pages.GraphVisual;
import sleepAppGUI.pages.HomePage;

public class WaterGraph extends GraphPage {
    @Override
    protected int pageNumber() {
        return 10;
    }

    @Override
    protected void setUp(Page page) {
        MyImage water_frame = new MyImage(page, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(page, new int[] {300, 10}, new int[] {500, 100}, "water", true);
        MyButton homebutton = new MyButton(page, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                WaterGraph.this.push(new HomePage());
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(page, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                WaterGraph.this.push(new GraphVisual());
                System.out.println("graph");
            }
        };
        
        MyBar screen_scatter = new MyBar(page, new int[] {80,120}, new int[] {700,470});
        screen_scatter.addPoint("Thursday",5.00);
        screen_scatter.addPoint("Wednesday",3.00);
        screen_scatter.addPoint("Friday",4.00);

        super.setUp(page);
    }
}
