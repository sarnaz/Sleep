package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.*;

public class SleepQuestions extends QuestionsPage {
    @Override
    protected int pageNumber() {
        return 9;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        new MyText(page, new int[] {170, 220}, new int[] {195, 240}, "How many hours of sleep did you get last night? ");
        new MyText(page, new int[] {290, 250}, new int[] {305, 270}, "(to the nearest hour)");
        MyTextField numberHours = new MyTextField(main, page, new int[] {380, 255}, new int[] {420, 280});
        // quality of sleep - can we implement a slider?
        new MyText(page, new int[] {220, 310}, new int[] {245, 330}, "Rate the quality of your sleep (1-10) ");
        MyTextField rateQuality = new MyTextField(main, page, new int[] {380, 325}, new int[] {420, 350});

        // Add next button
        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                SleepQuestions.this.push(new WaterQuestions());
                System.out.println("Water questions");
            }
        };
    }
}
