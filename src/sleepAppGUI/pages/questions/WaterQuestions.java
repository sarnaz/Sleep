package sleepAppGUI.pages.questions;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;

import java.util.Calendar;
import java.util.Date;

public class WaterQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 10;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);
        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "waterLogo", true);
        // number cups water
        new MyText(page, new int[] {195, 220}, new int[] {220, 240}, "How many cups of water have you had?");
        MyTextField numCups = new MyTextField(main, page, new int[] {380, 240}, new int[] {420, 265});
        // number of cups immediately before bed
        new MyText(page, new int[] {180, 310}, new int[] {205, 330}, "How many cups in the two hours before sleep?");
        MyTextField beforeBed = new MyTextField(main, page, new int[] {380, 325}, new int[] {420, 350});

        // Add next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                boolean valid = true;
                int totCups = 0;
                int bed = 0;
                try{
                    totCups = Integer.parseInt(numCups.getText());
                    bed = Integer.parseInt(beforeBed.getText());
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid");
                    valid = false;
                }
                if(valid == true){
                    // write to the DB here!!
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int date = calendar.get(Calendar.DAY_OF_MONTH);

                    Object[][] factors_chosen = Database.getFactorArray();
                    if ((Boolean) factors_chosen[1][5]){
                        WaterQuestions.this.push(new ScreenTimeQuestions());
                    }
                    else {
                        WaterQuestions.this.push(new HomePage());
                    }
                }
            }
        };
    }
}
