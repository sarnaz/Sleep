package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;

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
                    ScreenTimeQuestions.this.push(new HomePage());
                    System.out.println("Home");
                }

            }
        };
    }

}
