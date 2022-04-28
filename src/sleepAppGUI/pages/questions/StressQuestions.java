package sleepAppGUI.pages.questions;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;

import java.util.Calendar;
import java.util.Date;

public class StressQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 14;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "stressLogo", true);
        // daily stress
        MyText.putTextCentred(page, new int[] {400, 260}, 20, "Rate your average stress level today (1-5):");
        MyTextField averageStress = new MyTextField(main, page, new int[] {380, 280}, new int[] {420, 305});
        // Add next button
        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next") {
            public void isClicked()
            {
                boolean valid = true;
                int stress = 0;
                try{
                    stress = Integer.parseInt(averageStress.getText());
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid");
                    valid = false;
                }
                if (valid) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    Database.addStressEntry(stress, date, month, year);
                    Object[][] factors_chosen = Database.getFactorArray();
                    if ((Boolean) factors_chosen[1][4]) {
                        StressQuestions.this.push(new WaterQuestions());
                    } else if ((Boolean) factors_chosen[1][5]) {
                        StressQuestions.this.push(new ScreenTimeQuestions());
                    } else {
                        StressQuestions.this.push(new HomePage());
                    }
                }
            }
        };
    }
}
