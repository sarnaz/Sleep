package sleepAppGUI.pages.questions;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;

import java.util.Calendar;
import java.util.Date;


public class ScreenTimeQuestions extends QuestionsPage{

    @Override
    protected int pageNumber() {
        return 18;
    }


    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {230, 15}, new int[] {330, 160}, "phoneLogo", true);
        // amount of screen time
        new MyText(page, new int[] {190, 260}, new int[] {215, 280}, "How much time have you spent on a screen?");
        new MyText(page, new int[] {290, 290}, new int[] {305, 310}, "(to the nearest hour)");
        MyTextField screenHours = new MyTextField(main, page, new int[] {380, 300}, new int[] {420, 325});

        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                boolean valid = true;
                int numHours = 0;
                try{
                    numHours = Integer.parseInt(screenHours.getText());
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid");
                    valid = false;
                }
                if(valid == true){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    Database.addScreenTimeEntry(numHours, date, month, year);
                    ScreenTimeQuestions.this.push(new HomePage());
                    System.out.println("Home");
                }

            }
        };
    }

}
