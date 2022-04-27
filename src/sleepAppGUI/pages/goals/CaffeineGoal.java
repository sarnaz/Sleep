package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.GoalPage;

public class CaffeineGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 30;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[]{0, 10}, new int[]{800, 519}, "caffeine_goal", true);
        MyTextField coffeeInput = new MyTextField(main, page, new int[]{265, 170}, new int[]{315, 210});
        MyTextField teaInput = new MyTextField(main, page, new int[]{385, 170}, new int[]{435, 210});
        MyTextField energyDrinkInput = new MyTextField(main, page, new int[]{645, 170}, new int[]{695, 210});

        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                CaffeineGoal.this.push(new GoalPage());
                System.out.println("Saved");
            }
        };

        super.setUp(page);
    }
}
