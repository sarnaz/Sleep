package sleepAppGUI.pages.questions.caffeine;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.ScreenTimeQuestions;
import sleepAppGUI.pages.questions.StressQuestions;
import sleepAppGUI.pages.questions.WaterQuestions;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;
import sleepAppGUI.pages.questions.exercise.ExerciseQuestions;

import java.util.Calendar;
import java.util.Date;

public class CaffeineYes extends CaffeineQuestions{

    @Override
    protected int pageNumber() {
        return 16;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        page.pushToFront(yesClicked);
        //MyText metres = new MyText(more_info_page, new int[] {320, 77}, new int[]{335, 92}, "m")
        MyText numEach = new MyText(page, new int[] {180, 290}, new int[] {205, 310}, "Please enter how many of each you have had:");
        // coffee questions
        MyText coffeeNum = new MyText(page, new int[] {200, 323}, new int[] {215, 343}, "Coffee:");
        MyTextField coffeeInput = new MyTextField(main, page, new int[] {282, 305}, new int[]{318, 330});
        // tea questions
        MyText teaNum = new MyText(page, new int[] {340, 323}, new int[] {355, 343}, "Tea:");
        MyTextField teaInput = new MyTextField(main, page, new int[] {392, 305}, new int[]{428, 330});
        // energy drinks
        MyText energyNum = new MyText(page, new int[] {448, 323}, new int[] {473, 343}, "Energy drinks:");
        MyTextField energyInput = new MyTextField(main, page, new int[] {590, 305}, new int[]{626, 330});
        // next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                boolean valid = true;
                int cof = 0;
                int tea = 0;
                int enrg = 0;
                try{
                    cof = Integer.parseInt(coffeeInput.getText());
                    tea = Integer.parseInt(teaInput.getText());
                    enrg = Integer.parseInt(energyInput.getText());
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid");
                    valid = false;
                }
                if(valid == true) {
                    int totCaffeine = (cof*95) + (tea*26) + (enrg*86);
                    // write to DB here!
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    Object[][] factors_chosen = Database.getFactorArray();
                    if ((Boolean) factors_chosen[1][1]) {
                        CaffeineYes.this.push(new AlcoholQuestions());
                    } else if ((Boolean) factors_chosen[1][2]) {
                        CaffeineYes.this.push(new ExerciseQuestions());
                    } else if ((Boolean) factors_chosen[1][3]) {
                        CaffeineYes.this.push(new StressQuestions());
                    } else if ((Boolean) factors_chosen[1][4]) {
                        CaffeineYes.this.push(new WaterQuestions());
                    } else {
                        CaffeineYes.this.push(new ScreenTimeQuestions());
                    }
                }
            }
        };
    }
}
