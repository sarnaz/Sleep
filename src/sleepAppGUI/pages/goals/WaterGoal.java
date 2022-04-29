package sleepAppGUI.pages.goals;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.GoalPage;

public class WaterGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 24;
    }

    @Override
    protected void setUp(Page page) {
        MyTextField input = new MyTextField(main, page, new int[]{375, 205}, new int[]{425, 245});

        new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                boolean valid = true;
                try {
                    Database.setGoals("cupsOfWater", Integer.parseInt(input.getText()));
                    System.out.println("Water Goal Saved");
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    valid = false;
                }
                if (valid) {
                    Database.getGoalData();
                }
                WaterGoal.this.push(new GoalPage());
            }
        };

        super.setUp(page);
    }

    @Override
    protected String pageTitle() {
        return "Water";
    }

    @Override
    protected String unit() {
        return "cups";
    }

    @Override
    protected String imageName() {
        return "water_goal";
    }
}
