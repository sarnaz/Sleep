package sleepAppGUI.pages.questions;
import java.util.Calendar;
import java.util.Date;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;
import sleepAppGUI.pages.questions.caffeine.CaffeineQuestions;
import sleepAppGUI.pages.questions.exercise.ExerciseQuestions;
import sleepAppGUI.visuals.ColourPalette;

public class SleepQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 9;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage.putImage(page, new int[] {200, 30}, 400, "logo");
        new MyRectangle(page, new int[] {120, 180}, new int[] {560, 237}, 30, ColourPalette.foregroundColour);

        new MyText(page, new int[] {170, 220}, new int[] {195, 240}, "How many hours of sleep did you get last night? ");
        new MyText(page, new int[] {290, 250}, new int[] {305, 270}, "(to the nearest hour)");
        MyTextField numberHours = new MyTextField(main, page, new int[] {380, 255}, new int[] {420, 280});
        // quality of sleep - can we implement a slider?
        new MyText(page, new int[] {220, 310}, new int[] {245, 330}, "Rate the quality of your sleep (1-10) ");
        MyTextField rateQuality = new MyTextField(main, page, new int[] {380, 325}, new int[] {420, 350});
        new MyText(page, new int[] {160, 370}, new int[] {185, 390}, "How long did it take you to get to sleep (in minutes)?");
        MyTextField howLong = new MyTextField(main, page, new int[] {380, 385}, new int[] {420, 410});
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 420}, new int[] {440, 465}, "next")
        {

            public void isClicked()
            {
                boolean valid = true;
                int qualityInt = 0;
                int numHours = 0;
                int lengthInt = 0;
                // validate the input
                String hourSleep = numberHours.getText();
                String quality = rateQuality.getText();
                String lengthToSleep = howLong.getText();
                try
                {
                    numHours = Integer.parseInt(hourSleep);
                    qualityInt = Integer.parseInt(quality);
                    lengthInt = Integer.parseInt(lengthToSleep);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Not valid");
                    valid = false;
                }
                if (valid){
                    Database.setQuestionsAnswered();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    System.out.println(date + "\t" + month + "\t" + year);
                    Database.addSleepEntry(numHours, lengthInt, qualityInt, date, month, year);
                    // display the next page
                    Object[][] factors_chosen = Database.getFactorArray();
                    Database.getFactors();
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


            }
        };
    }
}
