package sleepAppGUI.pages;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyText;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;

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
        new MyText(page, new int[] {270, 100}, new int[] {180, 150}, "Suggestions");
        MyText WaterSuggestion = new MyText(page, new int[] {20, 150}, new int[] {365, 170}, "WATER: You can try eating fruit/vegetables that have high levels of moisture.");
        MyText CaffeineSuggestion = new MyText(page, new int[] {20, 180}, new int[] {365, 200}, "CAFFEINE: You can try drinking more water, decaf coffee and check caffeine");
        MyText CaffeineSuggestion2 = new MyText(page, new int[] {20, 200}, new int[] {365, 220}, "mg on each product.");
        MyText AlcoholSuggestion = new MyText(page, new int[] {20, 230}, new int[] {365, 250}, "ALCOHOL: You can try drinking other liquids, taking fixed budged for drinks");
        MyText AlcoholSuggestion2 = new MyText(page, new int[] {20, 250}, new int[] {365, 270}, "when going out.");
        MyText StressSuggestion = new MyText(page, new int[] {20, 280}, new int[] {365, 300}, "STRESS: You can try having a healthy diet, exercise regularly and stop using");
        MyText StressSuggestion2 = new MyText(page, new int[] {20, 300}, new int[] {365, 320}, "nicotine products.");
        MyText ExerciseSuggestion = new MyText(page, new int[] {20, 330}, new int[] {365, 350}, "EXERCISE: You can try breaking workout down into small sessions,");
        MyText ExerciseSuggestion2 = new MyText(page, new int[] {20, 350}, new int[] {365, 370}, "exercise with a friend.");
        MyText Screen_timeSuggestion = new MyText(page, new int[] {20, 380}, new int[] {365, 400}, "SCREEN-TIME: You can try scheduling Do_not_Disturb or Bedtime mode");
        MyButton backButton = new MyButton(page, "back", new int[] {360, 420}, new int[] {440, 455}, "back_button")
        {
            public void isClicked()
            {
                SuggestionPage.this.push(new GoalPage());
            }
        };
    }
}
