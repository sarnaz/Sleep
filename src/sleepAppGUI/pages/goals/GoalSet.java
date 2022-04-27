package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GoalPage;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

abstract public class GoalSet extends UIViewPage {
    @Override
    protected void setUp(Page page) {

        MyImage.putImage(page, new int[] {300, 20}, 200, "logo");
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 370}, 50, Color.white);
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 90}, 50, ColourUtil.foregroundColour);
        MyButton backButton = new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                GoalSet.this.push(new GoalPage());
                System.out.println("Goal Page");
            }
        };
    }
}
