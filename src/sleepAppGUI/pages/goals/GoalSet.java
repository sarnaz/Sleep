package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GoalPage;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

abstract public class GoalSet extends UIViewPage {

    @Override
    protected void setUp(Page page) {

        MyImage.putImage(page, new int[] {300, 20}, 200, "logo");
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 390}, 50, Color.white);
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 90}, 50, ColourUtil.foregroundColour);

        MyImage.putImage(page, new int[] {250, 250}, 300, imageName());
        MyText.putText(page, new int[] {110, 158}, 40, pageTitle(), Color.white, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {290, 235}, 30, "Goal:", Color.black, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {431, 235}, 30, unit(), Color.black, "Helvetica", Font.BOLD);
        MyButton backButton = new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                GoalSet.this.push(new GoalPage());
                System.out.println("Goal Page");
            }
        };
    }

    abstract protected String pageTitle();

    abstract protected String unit();

    abstract protected String imageName();
}
