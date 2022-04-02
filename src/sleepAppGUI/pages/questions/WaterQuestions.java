package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.alcohol.AlcoholQuestions;

public class WaterQuestions extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 0;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "waterLogo", true);
        // number cups water
        new MyText(page, new int[] {195, 220}, new int[] {220, 240}, "How many cups of water have you had?");
        new MyTextField(main, page, new int[] {380, 240}, new int[] {420, 265});
        // number of cups immediately before bed
        new MyText(page, new int[] {180, 310}, new int[] {205, 330}, "How many cups in the two hours before sleep?");
        new MyTextField(main, page, new int[] {380, 325}, new int[] {420, 350});

        // Add next button
        new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")  {
            public void isClicked() {
                WaterQuestions.this.push(new AlcoholQuestions());
                System.out.println("Stress questions");
            }
        };
    }
}
