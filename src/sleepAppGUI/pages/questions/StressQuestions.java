package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.HomePage;

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
        new MyText(page, new int[] {195, 260}, new int[] {220, 280}, "Rate your average stress level today (1-5):");
        MyTextField averageStress = new MyTextField(main, page, new int[] {380, 280}, new int[] {420, 305});
        // Add next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                StressQuestions.this.push(new HomePage());
                System.out.println("Back to home");
            }
        };
    }
}
