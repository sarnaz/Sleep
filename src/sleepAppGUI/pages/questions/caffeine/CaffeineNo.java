package sleepAppGUI.pages.questions.caffeine;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.pages.questions.ScreenTimeQuestions;
import sleepAppGUI.pages.questions.StressQuestions;
import sleepAppGUI.pages.questions.WaterQuestions;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;
import sleepAppGUI.pages.questions.exercise.ExerciseQuestions;

public class CaffeineNo extends CaffeineQuestions {

    @Override
    protected int pageNumber() {
        return 17;
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
                if(Boolean.valueOf((Boolean)factors_chosen[1][1])){
                    CaffeineNo.this.push(new AlcoholQuestions());
                }
                else if(Boolean.valueOf((Boolean)factors_chosen[1][2])){
                    CaffeineNo.this.push(new ExerciseQuestions());
                }
                else if(Boolean.valueOf((Boolean)factors_chosen[1][3])){
                    CaffeineNo.this.push(new StressQuestions());
                }
                else if(Boolean.valueOf((Boolean)factors_chosen[1][4])){
                    CaffeineNo.this.push(new WaterQuestions());
                }
                else{
                    CaffeineNo.this.push(new ScreenTimeQuestions());
                }
            }
        };
    }
}
