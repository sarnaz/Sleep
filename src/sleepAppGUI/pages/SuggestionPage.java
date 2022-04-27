package sleepAppGUI.pages;

import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

public class SuggestionPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 22;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        MyImage.putImage(page, new int[] {300, 30}, 200, "logo");
        new MyRectangle(page, new int[] {40, 120}, new int[] {720, 350}, 30, ColourUtil.foregroundColour);

        MyText.putText(page, new int[] {50, 160}, 30, "Suggestions", Color.white, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {50, 200}, 20, "WATER: You can try eating fruit/vegetables that have high levels of moisture.");
        MyText.putText(page, new int[] {50, 230}, 20, "CAFFEINE: You can try drinking more water, decaf coffee and check caffeine");
        MyText.putText(page, new int[] {50, 250}, 20, "mg on each product.");
        MyText.putText(page, new int[] {50, 280}, 20, "ALCOHOL: You can try drinking other liquids, taking fixed budged for drinks");
        MyText.putText(page, new int[] {50, 300}, 20, "when going out.");
        MyText.putText(page, new int[] {50, 330}, 20, "STRESS: You can try having a healthy diet, exercise regularly and stop using");
        MyText.putText(page, new int[] {50, 350}, 20, "nicotine products.");
        MyText.putText(page, new int[] {50, 380}, 20, "EXERCISE: You can try breaking workout down into small sessions,");
        MyText.putText(page, new int[] {50, 400}, 20, "exercise with a friend.");
        MyText.putText(page, new int[] {50, 430}, 20, "SCREEN-TIME: You can try scheduling \"Do Not Disturb\" or Bedtime mode");
        MyButton backButton = new MyButton(page, "back", new int[] {345, 500}, new int[] {455, 538}, "back_button")
        {
            public void isClicked()
            {
                SuggestionPage.this.push(new GoalPage());
            }
        };
    }
}
