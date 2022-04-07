package sleepAppGUI.pages.questions;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;
import sleepAppGUI.pages.questions.caffeine.CaffeineQuestions;
import sleepAppGUI.pages.questions.exercise.ExerciseQuestions;

public class SleepQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 9;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        new MyText(page, new int[] {170, 220}, new int[] {195, 240}, "How many hours of sleep did you get last night? ");
        new MyText(page, new int[] {290, 250}, new int[] {305, 270}, "(to the nearest hour)");
        MyTextField numberHours = new MyTextField(main, page, new int[] {380, 255}, new int[] {420, 280});
        // quality of sleep - can we implement a slider?
        new MyText(page, new int[] {220, 310}, new int[] {245, 330}, "Rate the quality of your sleep (1-10) ");
        MyTextField rateQuality = new MyTextField(main, page, new int[] {380, 325}, new int[] {420, 350});

        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                Database.getFactors();
                Object[][] factors_chosen = Database.getFactorArray();

                if ((Boolean) factors_chosen[1][0]){
                    SleepQuestions.this.push(new CaffeineQuestions());
                }
                else if ((Boolean)factors_chosen[1][1]){
                    SleepQuestions.this.push(new AlcoholQuestions());
                }
                else if ((Boolean)factors_chosen[1][2]){
                    SleepQuestions.this.push(new ExerciseQuestions());
                }
                else if ((Boolean)factors_chosen[1][3]){
                    SleepQuestions.this.push(new StressQuestions());
                }
                else if ((Boolean)factors_chosen[1][4]){
                    SleepQuestions.this.push(new WaterQuestions());
                }
                else{
                    SleepQuestions.this.push(new ScreenTimeQuestions());
                }
            }
        };
    }
}
