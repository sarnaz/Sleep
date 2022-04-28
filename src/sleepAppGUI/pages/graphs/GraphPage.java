package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GraphVisual;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.visuals.ColourPalette;

import java.awt.*;
import java.util.Calendar;

abstract public class GraphPage extends UIViewPage {

    Calendar calendar = Calendar.getInstance();
    String[] dayOfWeek = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
    @Override
    protected void setUp(Page page) {

        MyImage.putImage(page, new int[] {300, 20}, 200, "logo");
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 390}, 50, Color.white);
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 90}, 50, ColourPalette.foregroundColour);

        new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "home_button") {
            public void isClicked() {
                GraphPage.this.push(new HomePage());
                System.out.println("Home Page");
            }
        };

        new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                GraphPage.this.push(new GraphVisual());
                System.out.println("Graph Page");
            }
        };
    }
}
