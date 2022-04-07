package sleepAppGUI.pages.questions.alcohol;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.pages.questions.ScreenTimeQuestions;
import sleepAppGUI.pages.questions.StressQuestions;
import sleepAppGUI.pages.questions.WaterQuestions;
import sleepAppGUI.pages.questions.exercise.ExerciseQuestions;

public class AlcoholNo extends AlcoholQuestions {

    @Override
    protected int pageNumber() {
        return 13;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage noClicked = new MyImage(page, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        page.pushToFront(noClicked);
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                Object[][] factors_chosen = Database.getFactorArray();
                if ((Boolean) factors_chosen[1][2]){
                    AlcoholNo.this.push(new ExerciseQuestions());
                }
                else if ((Boolean) factors_chosen[1][3]){
                    AlcoholNo.this.push(new StressQuestions());
                }
                else if ((Boolean) factors_chosen[1][4]){
                    AlcoholNo.this.push(new WaterQuestions());
                }
                else if ((Boolean) factors_chosen[1][5]){
                    AlcoholNo.this.push(new ScreenTimeQuestions());
                }
                else{
                    AlcoholNo.this.push(new HomePage());
                }
            }
        };
    }
}
