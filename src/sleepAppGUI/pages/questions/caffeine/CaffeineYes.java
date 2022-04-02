package sleepAppGUI.pages.questions.caffeine;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.StressQuestions;

public class CaffeineYes extends CaffeineQuestions{

    @Override
    protected int pageNumber() {
        return 16;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        page.pushToFront(yesClicked);
        //MyText metres = new MyText(more_info_page, new int[] {320, 77}, new int[]{335, 92}, "m")
        MyText numEach = new MyText(page, new int[] {180, 290}, new int[] {205, 310}, "Please enter how many of each you have had:");
        // coffee questions
        MyText coffeeNum = new MyText(page, new int[] {200, 323}, new int[] {215, 343}, "Coffee:");
        MyTextField coffeeInput = new MyTextField(main, page, new int[] {282, 305}, new int[]{318, 330});
        // tea questions
        MyText teaNum = new MyText(page, new int[] {340, 323}, new int[] {355, 343}, "Tea:");
        MyTextField teaInput = new MyTextField(main, page, new int[] {392, 305}, new int[]{428, 330});
        // energy drinks
        MyText energyNum = new MyText(page, new int[] {448, 323}, new int[] {473, 343}, "Energy drinks:");
        MyTextField energyInput = new MyTextField(main, page, new int[] {590, 305}, new int[]{626, 330});

        // next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                CaffeineYes.this.push(new StressQuestions());
                System.out.println("Stress");
            }
        };
    }
}
