package sleepAppGUI.pages.questions.exercise;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.pages.questions.ScreenTimeQuestions;
import sleepAppGUI.pages.questions.StressQuestions;
import sleepAppGUI.pages.questions.WaterQuestions;

public class ExerciseYes extends ExerciseQuestions {

    @Override
    protected int pageNumber() {
        return 20;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        page.pushToFront(yesClicked);
        MyText amountEx = new MyText(page, new int[] {200, 290}, new int[] {225, 310}, "Please enter how much you have done:");
        MyText nearestHour = new MyText(page, new int[]{290, 320}, new int[]{305, 340}, "(to the nearest hour)");
        MyTextField exerciseHours = new MyTextField(main, page, new int[]{380, 330}, new int[]{420, 355});
        // next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                Object[][] factors_chosen = Database.getFactorArray();
                if ((Boolean)factors_chosen[1][3]){
                    ExerciseYes.this.push(new StressQuestions());
                }
                else if ((Boolean)factors_chosen[1][4]){
                    ExerciseYes.this.push(new WaterQuestions());
                }
                else if ((Boolean)factors_chosen[1][5]){
                    ExerciseYes.this.push(new ScreenTimeQuestions());
                }
                else{
                    ExerciseYes.this.push(new HomePage());
                }
            }
        };
    }
}
