package sleepAppGUI.pages.questions.exercise;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyText;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.questions.QuestionsPage;

public class ExerciseQuestions extends QuestionsPage {

    @Override
    protected int pageNumber() {
        return 19;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "exerciseLogo", true);
        // Have you exercised?
        new MyText(page, new int[] {300, 220}, new int[] {325, 240}, "Have you exercised?");

        new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked")
        {
            public void isClicked()
            {
                ExerciseQuestions.this.push(new ExerciseYes());
                System.out.println("yes");

            }
        };

        new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                ExerciseQuestions.this.push(new ExerciseNo());
                System.out.println("no");
            }
        };
    }
}
