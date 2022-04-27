package sleepAppGUI.pages.goals;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GoalPage;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

public class CaffeineGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 30;
    }

    @Override
    protected void setUp(Page page) {

        MyImage.putImage(page, new int[] {300, 20}, 200, "logo");
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 390}, 50, Color.white);
        new MyRectangle(page, new int[] {80, 100}, new int[] {640, 90}, 50, ColourUtil.foregroundColour);

        MyImage.putImage(page, new int[] {250, 250}, 300, this.imageName());
        MyText.putText(page, new int[] {110, 158}, 40, pageTitle(), Color.white, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {130, 235}, 26, "Goal: Coffee", Color.black, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {345, 235}, 26, "Tea", Color.black, "Helvetica", Font.BOLD);
        MyText.putText(page, new int[] {460, 235}, 26, "Energy Drink", Color.black, "Helvetica", Font.BOLD);

        MyTextField coffeeInput = new MyTextField(main, page, new int[] {295, 205}, new int[]{340, 245});
        MyTextField teaInput = new MyTextField(main, page, new int[] {395, 205}, new int[]{445, 245});
        MyTextField energyDrinkInput = new MyTextField(main, page, new int[] {630, 205}, new int[]{695, 245});

        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                boolean blank = true;

                if (!teaInput.getText().isBlank()) {
                    try {
                        Database.setGoals("tea", Integer.parseInt(teaInput.getText()));
                        blank = false;
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                if (!coffeeInput.getText().isBlank()) {
                    try {
                        Database.setGoals("coffee", Integer.parseInt(coffeeInput.getText()));
                        blank = false;
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                if (!energyDrinkInput.getText().isBlank()) {
                    try {
                        Database.setGoals("energyDrinks", Integer.parseInt(energyDrinkInput.getText()));
                        blank = false;
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                if (!blank) {
                    Object Goal_array[][] = Database.getGoalData();
                }
                CaffeineGoal.this.push(new GoalPage());
            }
        };

        new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                CaffeineGoal.this.push(new GoalPage());
                System.out.println("Goal Page");
            }
        };
    }

    @Override
    protected String pageTitle() {
        return "Caffeine";
    }

    @Override
    protected String unit() {
        return null;
    }

    @Override
    protected String imageName() {
        return "caffeine_goal";
    }
}
