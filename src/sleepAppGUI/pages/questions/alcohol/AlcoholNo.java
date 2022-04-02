package sleepAppGUI.pages.questions.alcohol;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.questions.StressQuestions;

public class AlcoholNo extends AlcoholQuestions {

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage noClicked = new MyImage(page, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        page.pushToFront(noClicked);
        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                AlcoholNo.this.push(new StressQuestions());
                System.out.println("Stress");
            }
        };
    }
}
