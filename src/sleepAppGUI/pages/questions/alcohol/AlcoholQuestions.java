package sleepAppGUI.pages.questions.alcohol;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.QuestionsPage;

public class AlcoholQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 0;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "alcoholLogo", true);

        //MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", false);
        //MyImage noClicked = new MyImage(page, new int[] {425, 230}, new int[] {475, 265}, "noButton", false);

        // did you drink alcohol?
        new MyText(page, new int[] {245, 220}, new int[] {270, 240}, "Have you consumed alcohol?");
        new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked") {
            public void isClicked() {
                AlcoholQuestions.this.push(new AlcoholYes());
                System.out.println("yes");

            }
        };
        new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                AlcoholQuestions.this.push(new AlcoholNo());
                System.out.println("no");
            }
        };
    }
}
