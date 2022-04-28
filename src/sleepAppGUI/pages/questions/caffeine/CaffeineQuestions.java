package sleepAppGUI.pages.questions.caffeine;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyText;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.questions.QuestionsPage;

public class CaffeineQuestions extends QuestionsPage {

    @Override
    protected int pageNumber() {
        return 15;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "coffeeLogo", true);
        // did you drink caffeine?
        new MyText(page, new int[] {245, 220}, new int[] {270, 240}, "Have you consumed caffeine?");
        new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked")
        {
            public void isClicked()
            {
                CaffeineQuestions.this.push(new CaffeineYes());
                System.out.println("yes");

            }
        };
        new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                CaffeineQuestions.this.push(new CaffeineNo());
                System.out.println("no");
            }
        };
    }
}
