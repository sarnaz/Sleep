package sleepAppGUI.pages.questions.alcohol;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.StressQuestions;

public class AlcoholYes extends AlcoholQuestions {

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        page.pushToFront(yesClicked);

        new MyText(page, new int[] {245, 290}, new int[] {270, 310}, "How many units have you had?");
        MyTextField howMany = new MyTextField(main, page, new int[] {380, 305}, new int[] {420, 330});

        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                AlcoholYes.this.push(new StressQuestions());
                System.out.println("Stress");
            }
        };
    }
}
