package sleepAppGUI.pages.questions.exercise;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.HomePage;

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
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                ExerciseNo.this.push(new HomePage());
                System.out.println("Home");
            }
        };
    }
}
