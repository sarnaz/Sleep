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
        new MyText(page, new int[] {270, 110}, new int[] {180, 160}, "Suggestions");
        //MyText WaterSuggestion = new MyText(suggestions, new int[] {350, 200}, new int[] {365, 220}, "You should drink 6 cups of water per day on average.");
        //I need a suggestion for each person from the factor they researched on and I need data from the back end to suggest how to improve(eg. if 4 cups of water then drink 1 more
        MyButton backButton = new MyButton(page, "back", new int[] {360, 400}, new int[] {440, 435}, "back_button")
        {
            public void isClicked()
            {
                SuggestionPage.this.push(new HomePage());
            }
        };
    }
}
