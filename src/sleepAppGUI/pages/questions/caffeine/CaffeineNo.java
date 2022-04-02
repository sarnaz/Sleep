package sleepAppGUI.pages.questions.caffeine;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.HomePage;

public class CaffeineNo extends CaffeineQuestions {

    @Override
    protected int pageNumber() {
        return 17;
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
                CaffeineNo.this.push(new HomePage());
                System.out.println("Home");
            }
        };
    }
}
