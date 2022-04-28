package sleepAppGUI.pages.questions.exercise;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.pages.questions.ScreenTimeQuestions;
import sleepAppGUI.pages.questions.StressQuestions;
import sleepAppGUI.pages.questions.WaterQuestions;

import java.util.Calendar;
import java.util.Date;

public class ExerciseNo extends ExerciseQuestions {

    @Override
    protected int pageNumber() {
        return 21;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage noClicked = new MyImage(page, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        page.pushToFront(noClicked);
        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                // write to the database here!! 0 hrs
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                Database.addFitnessEntry(0, 0, date, month, year);
                Object[][] factors_chosen = Database.getFactorArray();
                if ((Boolean) factors_chosen[1][3]){
                    ExerciseNo.this.push(new StressQuestions());
                }
                else if ((Boolean) factors_chosen[1][4]){
                    ExerciseNo.this.push(new WaterQuestions());
                }
                else if ((Boolean) factors_chosen[1][5]){
                    ExerciseNo.this.push(new ScreenTimeQuestions());
                }
                else{
                    ExerciseNo.this.push(new HomePage());
                }
            }
        };
    }
}
